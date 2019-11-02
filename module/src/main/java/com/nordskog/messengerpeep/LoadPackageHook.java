package com.nordskog.messengerpeep;

import android.util.Log;


import com.mayulive.xposed.classhunter.ClassHunter;
import com.mayulive.xposed.classhunter.ProfileCache;
import com.mayulive.xposed.classhunter.ProfileHelpers;
import com.mayulive.xposed.classhunter.ProfileServer;
import com.mayulive.xposed.classhunter.packagetree.PackageTree;
import com.mayulive.xposed.classhunter.profiles.MethodProfile;
import com.nordskog.messengerpeep.Thrift.ThriftObject;
import com.nordskog.messengerpeep.Thrift.ThriftParser;
import com.nordskog.messengerpeep.Thrift.ThriftSearcher;


import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.apache.thrift.transport.TTransport;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;


import static com.mayulive.xposed.classhunter.Modifiers.*;


public class LoadPackageHook implements IXposedHookLoadPackage

{
	private static String LOGTAG = "###";

	private static String MESSENGER_PACKAGE = "com.facebook.orca";

	@Override
	public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable
	{


		/////////////////
		// Messenger
		/////////////////


		if (lpparam.packageName.equals(MESSENGER_PACKAGE) )
		{
			XposedBridge.log(LOGTAG+", "+"Module loaded in "+lpparam.packageName);

			Log.i(LOGTAG, "Module loaded in"+lpparam.packageName);

			PackageTree classTree = new PackageTree(lpparam.appInfo.sourceDir, lpparam.classLoader);

			//ProfileServer server = new ProfileServer(8282, classTree.getClassLoader(), classTree);
			//server.start();




			try
			{

				int CRC32 = ClassHunter.getApkCRC32(lpparam.appInfo.sourceDir);
				ProfileCache.setSaveLocation(lpparam.appInfo.dataDir+"/files/MESSENGER_PEEP_CLASS_CACHE");

				ClassHunter.MODULE_SIGNATURE = BuildConfig.VERSION_CODE;
				ClassHunter.HOOK_SIGNATURE = CRC32;


				ProfileCache.loadCache();

				Profiles.init(classTree);

				hookByteGenerator(classTree);
				hookPublish(classTree);
				hookConnect(classTree);

				hookIncomingPublishRaw(classTree);

				ProfileCache.saveCache();

				Log.i(LOGTAG, "Finished hook setup");
			}
			catch ( Exception ex  )
			{
				Log.e(LOGTAG, "Shit hit fan");
				Log.e(LOGTAG, ex.toString());
				ex.printStackTrace();
			}




		}

	}

	//////////////////////
	// Outgoing
	//////////////////////


	private static void hookPublish( PackageTree classTree ) throws Exception
	{

		Class clazz = ClassHunter.loadClass("com.facebook.proxygen.MQTTClient", classTree.getClassLoader());

		//Log.i(LOGTAG, "Clazz: "+clazz.toString());

		/*
		MethodProfile publishProfile = new MethodProfile(
				NATIVE
		);

		Method method = ProfileHelpers.findFirstMatchingMethodWithName(publishProfile, clazz.getDeclaredMethods(), clazz, "publish");
		*/


		MethodProfile publishProfile = new MethodProfile(
				PUBLIC|EXACT
		);

		Method method = ProfileHelpers.findFirstMatchingMethodWithName(publishProfile, clazz.getDeclaredMethods(), clazz, "publish");


		//Log.i(LOGTAG, "publish method: "+method.toString());

		Log.i(LOGTAG, "Publish class: "+clazz.toString());
		Log.i(LOGTAG, "Publish method: "+method.toString());


		XposedBridge.hookMethod(method, new XC_MethodHook()
		{
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable
			{
				Log.i(LOGTAG, " ");
				Log.i(LOGTAG, "############################");
				Log.i(LOGTAG, "####### PUBLISH START ######");
				Log.i(LOGTAG, "############################");
				Log.i(LOGTAG, " ");

				Log.i(LOGTAG, " ");

				Log.i(LOGTAG, "Topic: "+param.args[0].toString());

				byte[] buffer = (byte[])param.args[1];

				Log.i(LOGTAG, "Bytes: "+bytesToHex( buffer ));

				Log.i(LOGTAG, " ");

				//ThriftObject thriftObject = decodeThriftObject(buffer);
				//Log.i(LOGTAG, thriftObject.toString());

				List<ThriftObject> objects = decodeMultipleThriftObjects(buffer);
				if (objects.size() > 1)
				{
					Log.i(LOGTAG, "Stream contained multiple objects: "+objects.size());
				}

				for (ThriftObject object : objects)
				{
					Log.i(LOGTAG, object.toString());
				}

				Log.i(LOGTAG, " ");
				Log.i(LOGTAG, "############################");
				Log.i(LOGTAG, "######## PUBLISH END #######");
				Log.i(LOGTAG, "############################");
				Log.i(LOGTAG, " ");

			}
		});
	}


	private static void hookConnect( PackageTree classTree ) throws Exception
	{

		Class clazz = ProfileHelpers.loadProfiledClass(HunterProfiles.getConnectRunnableClassProfile(), classTree);

		Log.i(LOGTAG, "Connect class: "+clazz.toString());


		Method method = ProfileHelpers.findFirstMethodByName(clazz.getDeclaredMethods(), "run");

		Log.i(LOGTAG, "Connect method: "+method.toString());


		Field byteArrayField = ProfileHelpers.findFirstDeclaredFieldWithType( byte[].class, clazz );
		byteArrayField.setAccessible(true);

		Field addressField = ProfileHelpers.findFirstDeclaredFieldWithType( String.class, clazz );
		addressField.setAccessible(true);

		XposedBridge.hookMethod(method, new XC_MethodHook()
		{
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable
			{
				Log.i(LOGTAG, " ");
				Log.i(LOGTAG, "############################");
				Log.i(LOGTAG, "####### CONNECT START ######");
				Log.i(LOGTAG, "############################");
				Log.i(LOGTAG, " ");

				Log.i(LOGTAG, " ");

				String url = (String) addressField.get(param.thisObject);

				Log.i(LOGTAG, "Url: "+url);

				byte[] buffer = (byte[]) byteArrayField.get(param.thisObject);

				Log.i(LOGTAG, "Bytes: "+bytesToHex( buffer ));

				Log.i(LOGTAG, " ");

				//ThriftObject thriftObject = decodeThriftObject(buffer);
				//Log.i(LOGTAG, thriftObject.toString());

				List<ThriftObject> objects = decodeMultipleThriftObjects(buffer);
				if (objects.size() > 1)
				{
					Log.i(LOGTAG, "Stream contained multiple objects: "+objects.size());
				}

				for (ThriftObject object : objects)
				{
					Log.i(LOGTAG, object.toString());
				}

				Log.i(LOGTAG, " ");
				Log.i(LOGTAG, "############################");
				Log.i(LOGTAG, "######## CONNECT END #######");
				Log.i(LOGTAG, "############################");
				Log.i(LOGTAG, " ");

			}
		});
	}



	private static void hookByteGenerator( PackageTree classTree  )
	{
		//Class clazz = ClassHunter.loadClass("X.1Ts", classTree.getClassLoader());

		Class clazz = ProfileHelpers.loadProfiledClass(HunterProfiles.getPayloadByteWriterClassProfile(Profiles.PayloadInterfaceClass), classTree);

		Log.i(LOGTAG, "Payload byte writer class: "+clazz.toString());

		Method method = ProfileHelpers.findAllMethodsWithReturnType( byte[].class, clazz.getDeclaredMethods() ).get(0);

		Log.i(LOGTAG, "Payload byte writer method: "+method.toString());

		XposedBridge.hookMethod(method, new XC_MethodHook()
		{
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable
			{

				Object ret = param.args[0];

				Log.i(LOGTAG, " ");
				Log.i(LOGTAG, "############################");
				Log.i(LOGTAG, "#####  ENCODING OBJECT  ####");
				Log.i(LOGTAG, "############################");
				Log.i(LOGTAG, " ");
				Log.i(LOGTAG, ret.toString());

				byte[] buffer = (byte[])param.getResult();

				Log.i(LOGTAG, "Bytes: "+bytesToHex( buffer ));

				Log.i(LOGTAG, " ");

			}
		});
	}




	////////////////////
	// Incoming
	////////////////////

	private static void hookIncomingPublishRaw( PackageTree classTree  )
	{
		//Class clazz = ClassHunter.loadClass("X.1TW", classTree.getClassLoader());
		//Log.i(LOGTAG, "Clazz: "+clazz.toString());

		Class clazz = ProfileHelpers.loadProfiledClass(HunterProfiles.getThriftClientCallbackClassProfile(), classTree);


		Method method = ProfileHelpers.findFirstMethodByName( clazz.getDeclaredMethods(), "onPublish" );

		Log.i(LOGTAG, "ThriftClientCallback class: "+clazz.toString());
		Log.i(LOGTAG, "ThriftClientCallback method: "+method.toString());

		//Log.i(LOGTAG, "method: "+method.toString());

		XposedBridge.hookMethod(method, new XC_MethodHook()
		{
			@Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable
			{

				Log.i(LOGTAG, " ");
				Log.i(LOGTAG, "############################");
				Log.i(LOGTAG, "#### REC PUBLISH START  ####");
				Log.i(LOGTAG, "############################");
				Log.i(LOGTAG, " ");

				String topic = ( String ) param.args[0];

				byte[] buffer = (byte[]) param.args[1];


				Log.i(LOGTAG, "Topic: "+topic);
				Log.i(LOGTAG, "Bytes: "+bytesToHex( buffer ));


				//ThriftObject thriftObject = decodeThriftObject(buffer);
				//Log.i(LOGTAG, thriftObject.toString());

				List<ThriftObject> objects = decodeMultipleThriftObjects(buffer);
				if (objects.size() > 1)
				{
					Log.i(LOGTAG, "Stream contained multiple objects: "+objects.size());
				}

				for (ThriftObject object : objects)
				{
					Log.i(LOGTAG, object.toString());
				}

				Log.i(LOGTAG, " ");
				Log.i(LOGTAG, "############################");
				Log.i(LOGTAG, "####   REC PUBLISH END  ####");
				Log.i(LOGTAG, "############################");
				Log.i(LOGTAG, " ");

			}
		});
	}

	/////////////////
	// Util
	/////////////////

	private static List<ThriftObject> decodeMultipleThriftObjects( byte[] buffer )
	{
		if (buffer[0] == 0x00)
		{
			Log.i(LOGTAG, "Removing extra null at beginning");
			byte[] newBuffer = new byte[buffer.length-1];
			for (int i = 1; i < buffer.length; i++)
			{
				newBuffer[i-1] = buffer[i];
			}

			buffer = newBuffer;
		}

		TTransport trans = new TMemoryInputTransport(buffer);

		TCompactProtocol prot = new TCompactProtocol(trans);


		List<ThriftObject> thirftObjects =  ThriftParser.readConsecutiveStructure(prot );
		for (ThriftObject object : thirftObjects)
		{
			ThriftSearcher.recursivelyMatchAndName(object, null);
		}


		return thirftObjects;


	}

	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

}



