package com.nordskog.messengerpeep;

import android.util.Log;

import com.mayulive.xposed.classhunter.ClassHunter;
import com.mayulive.xposed.classhunter.ProfileHelpers;
import com.mayulive.xposed.classhunter.packagetree.PackageEntry;
import com.mayulive.xposed.classhunter.packagetree.PackageTree;
import com.nordskog.messengerpeep.Thrift.ThriftObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Profiles
{

	/////////////////////////
	////////////////////////

	public static Class ConnectPayloadClass;

	public static Class PayloadInterfaceClass;

	/////////////////////////
	/////////////////////////

	public static Class FieldDefinitionClass;

	public static Field FieldDefinitionClass_fieldIdField;
	public static Field FieldDefinitionClass_nameIdField;
	public static Field FieldDefinitionClass_typedField;

	///////////////////////////
	///////////////////////////

	public static List<Class> PayloadClasses = new ArrayList<>();


	public static List<ThriftObject> parsedClasses = new ArrayList<>();



	public static void init (PackageTree tree) throws Exception
	{

		ConnectPayloadClass = ProfileHelpers.loadProfiledClass( HunterProfiles.getEncodeMessagePayloadClass(), tree );

		////////////////////
		////////////////////

		//PayloadInterfaceClass = ClassHunter.loadClass( "X.1Tk", tree.getClassLoader() );

		// First interface that is not Serializable or Cloneable is the payload interface
		for (Class anInterface : ConnectPayloadClass.getInterfaces())
		{
			if (anInterface != Serializable.class && anInterface != Cloneable.class )
			{
				PayloadInterfaceClass = anInterface;
				break;
			}
		}

		//////////////////////
		//////////////////////


		//FieldDefinitionClass = ClassHunter.loadClass( "X.1Tm", tree.getClassLoader() );

		// Class of field that occurs the most in this class is the field definition class.
		{
			HashMap<Class, Integer> classCountMap = new HashMap<>();

			for (Field field : ConnectPayloadClass.getDeclaredFields())
			{
				Class type = field.getType();
				Integer count = classCountMap.get(type);
				if (count == null)
					count = 0;
				count = count + 1;

				classCountMap.put(type, count);
			}

			int maxCount = 0;

			for (Map.Entry<Class, Integer> entry : classCountMap.entrySet())
			{
				if ( entry.getValue() > maxCount )
				{
					FieldDefinitionClass = entry.getKey();
					maxCount = entry.getValue();
				}
			}
		}



		///////////////////

		/*
		FieldDefinitionClass_fieldIdField = FieldDefinitionClass.getDeclaredField("B");
		FieldDefinitionClass_nameIdField = FieldDefinitionClass.getDeclaredField("C");
		FieldDefinitionClass_typedField = FieldDefinitionClass.getDeclaredField("D");
		*/

		Log.i("###", "Field definition class is: "+FieldDefinitionClass.toString());

		FieldDefinitionClass_nameIdField = ProfileHelpers.findFirstDeclaredFieldWithType( String.class, FieldDefinitionClass);
		FieldDefinitionClass_fieldIdField = ProfileHelpers.findFirstDeclaredFieldWithType( short.class, FieldDefinitionClass);
		FieldDefinitionClass_typedField = ProfileHelpers.findFirstDeclaredFieldWithType( byte.class, FieldDefinitionClass);

		FieldDefinitionClass_fieldIdField.setAccessible(true);
		FieldDefinitionClass_nameIdField.setAccessible(true);
		FieldDefinitionClass_typedField.setAccessible(true);

		/////////////////////
		/////////////////////

		long nullCounter = 0;

		for (Map.Entry<String, PackageEntry> entry : tree.getMap().entrySet())
		{
			for (String className : entry.getValue().classes)
			{
				Class candidate = ClassHunter.loadClass( className, tree.getClassLoader()  );

				if (candidate == null)
				{
					nullCounter++;
					continue;
				}

				if (candidate.getInterfaces().length < 1)
				{
					continue;
				}

				for (Class anInterface : candidate.getInterfaces())
				{
					if (anInterface == PayloadInterfaceClass)
					{
						PayloadClasses.add(candidate);
						break;
					}
				}

			}
		}

		//Log.i("###", "Encountered "+nullCounter+" null classes");
		//Log.i("###", "Got "+PayloadClasses.size()+" payload classes");

		StringBuilder builder = new StringBuilder();

		for (Class payloadClass : PayloadClasses)
		{
			builder.append(payloadClass.getCanonicalName()+"\n");

			ThriftObject thriftObject = ThriftObject.fromClass(payloadClass);
			parsedClasses.add( thriftObject );

		}

		//Log.i("###", builder.toString());


	}






}
