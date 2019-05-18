package com.nordskog.messengerpeep;

import com.mayulive.xposed.classhunter.profiles.ClassItem;
import com.mayulive.xposed.classhunter.profiles.ClassProfile;
import com.mayulive.xposed.classhunter.profiles.ConstructorProfile;
import com.mayulive.xposed.classhunter.profiles.FieldItem;
import com.mayulive.xposed.classhunter.profiles.MethodProfile;

import static com.mayulive.xposed.classhunter.Modifiers.*;

public class HunterProfiles
{
	public static ClassProfile getEncodeMessagePayloadClass()
	{
		ClassProfile newProfile = new ClassProfile();

		newProfile.setFullPath("X.1Tq");
		newProfile.setKnownPath("");

		newProfile.setMinDepth(1);
		newProfile.setMaxDepth(1);
		newProfile.setModifiers(PUBLIC );

		newProfile.setTypeParamCount(0);
		newProfile.setSuperClass(	new ClassItem(java.lang.Object.class));
/////////////////////////
//Interfaces
/////////////////////////
		newProfile.setInterfaces(new ClassItem[]
				{
						new ClassItem("" , PUBLIC | INTERFACE | ABSTRACT | EXACT ),
						new ClassItem(java.io.Serializable.class),
						new ClassItem(java.lang.Cloneable.class)

				});
/////////////////////////
//Nested Classes
/////////////////////////
		newProfile.setNestedClasses(new ClassItem[]
				{

				});
/////////////////////////
//Declared fields
/////////////////////////
		newProfile.setDeclaredFields(new FieldItem[]
				{
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.Long.class)),	//appId
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.Long.class)),	//clientCapabilities
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.String.class)),	//clientIpAddress
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.Long.class)),	//clientMqttSessionId
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.Byte.class)),	//clientStack
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.String.class)),	//clientType
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(byte[].class)),	//connectTokenHash
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.String.class)),	//deviceId
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.String.class)),	//deviceSecret
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.Long.class)),	//endpointCapabilities
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.Long.class)),	//fbnsConnectionKey
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.String.class)),	//fbnsConnectionSecret
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.String.class)),	//fbnsDeviceId
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.String.class)),	//fbnsDeviceSecret
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.Boolean.class)),	//isInitiallyForeground
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.Long.class)),	//luid
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.Boolean.class)),	//makeUserAvailableInForeground
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.Integer.class)),	//networkSubtype
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.Integer.class)),	//networkType
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.Integer.class)),	//networkTypeInfo
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.Boolean.class)),	//noAutomaticForeground
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.Boolean.class)),	//overrideNectarLogging
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.Integer.class)),	//publishFormat
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.String.class)),	//regionPreference
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.util.List.class)),	//subscribeTopics
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.String.class)),	//userAgent
						new FieldItem( PUBLIC | FINAL | EXACT , 	new ClassItem(java.lang.Long.class)),	//userId
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//B
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//C
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//D
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//E
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//F
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//G
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//H
						new FieldItem( PUBLIC | STATIC | EXACT , 	new ClassItem(boolean.class)),	//I
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//J
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//K
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//L
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//M
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//N
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//O
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//P
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//Q
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//R
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//S
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//T
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//U
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//V
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//W
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//X
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//Y
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//Z
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | FINAL | EXACT )),	//a
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//b
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//c
						new FieldItem( PRIVATE | STATIC | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//d

				});
/////////////////////////
//Declared Methods
/////////////////////////
		newProfile.setDeclaredMethods(new MethodProfile[]
				{
						//Method #0: A
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(boolean.class),

										new ClassItem("" , PUBLIC | THIS | EXACT )

								),

						//Method #1: ShC
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(java.lang.String.class),

										new ClassItem(int.class),
										new ClassItem(boolean.class)

								),

						//Method #2: equals
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(boolean.class),

										new ClassItem(java.lang.Object.class)

								),

						//Method #3: hashCode
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(int.class)

								),

						//Method #4: pj
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem("" , PUBLIC | INTERFACE | ABSTRACT | EXACT )

								),

						//Method #5: smC
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class),

										new ClassItem("" , PUBLIC | ABSTRACT | EXACT )

								),

						//Method #6: toString
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(java.lang.String.class)

								),


				});
/////////////////////////
//Declared Constructors
/////////////////////////
		newProfile.setDeclaredConstructors(new ConstructorProfile[]
				{
						//Constructor #0
						new ConstructorProfile
								(		PUBLIC | EXACT ,

										new ClassItem("" , PUBLIC | THIS | EXACT )

								),

						//Constructor #1
						new ConstructorProfile
								(		PUBLIC | EXACT ,

										new ClassItem(java.lang.Long.class),
										new ClassItem(java.lang.String.class),
										new ClassItem(java.lang.Long.class),
										new ClassItem(java.lang.Long.class),
										new ClassItem(java.lang.Integer.class),
										new ClassItem(java.lang.Boolean.class),
										new ClassItem(java.lang.Boolean.class),
										new ClassItem(java.lang.String.class),
										new ClassItem(java.lang.Boolean.class),
										new ClassItem(java.lang.Integer.class),
										new ClassItem(java.lang.Integer.class),
										new ClassItem(java.lang.Long.class),
										new ClassItem(java.lang.String.class),
										new ClassItem(java.util.List.class),
										new ClassItem(java.lang.String.class),
										new ClassItem(java.lang.Long.class),
										new ClassItem(java.lang.Boolean.class),
										new ClassItem(byte[].class),
										new ClassItem(java.lang.String.class),
										new ClassItem(java.lang.String.class),
										new ClassItem(java.lang.Byte.class),
										new ClassItem(java.lang.Long.class),
										new ClassItem(java.lang.String.class),
										new ClassItem(java.lang.String.class),
										new ClassItem(java.lang.String.class),
										new ClassItem(java.lang.Long.class),
										new ClassItem(java.lang.Integer.class)

								),


				});

		return newProfile;
	}



	public static ClassProfile getPayloadByteWriterClassProfile( Class payloadInterfaceClass )
	{
		ClassProfile newProfile = new ClassProfile();

		newProfile.setFullPath("X.1Ts");
		newProfile.setKnownPath("");

		newProfile.setMinDepth(1);
		newProfile.setMaxDepth(1);
		newProfile.setModifiers(PUBLIC );

		newProfile.setTypeParamCount(0);
		newProfile.setSuperClass(	new ClassItem(java.lang.Object.class));
/////////////////////////
//Interfaces
/////////////////////////
		newProfile.setInterfaces(new ClassItem[]
				{

				});
/////////////////////////
//Nested Classes
/////////////////////////
		newProfile.setNestedClasses(new ClassItem[]
				{

				});
/////////////////////////
//Declared fields
/////////////////////////
		newProfile.setDeclaredFields(new FieldItem[]
				{
						new FieldItem( PRIVATE | FINAL | EXACT , 	new ClassItem(java.io.ByteArrayOutputStream.class)),	//B
						new FieldItem( PRIVATE | EXACT , 	new ClassItem("" , PUBLIC | ABSTRACT | EXACT )),	//C
						new FieldItem( PRIVATE | FINAL | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//D

				});
/////////////////////////
//Declared Methods
/////////////////////////
		newProfile.setDeclaredMethods(new MethodProfile[]
				{
						//Method #0: A
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(byte[].class),

										new ClassItem(payloadInterfaceClass )

								),


				});
/////////////////////////
//Declared Constructors
/////////////////////////
		newProfile.setDeclaredConstructors(new ConstructorProfile[]
				{
						//Constructor #0
						new ConstructorProfile
								(		PUBLIC | EXACT ,

										new ClassItem[0]

								),

						//Constructor #1
						new ConstructorProfile
								(		PUBLIC | EXACT ,

										new ClassItem("" , PUBLIC | INTERFACE | ABSTRACT | EXACT )

								),


				});

		return newProfile;
	}




	public static ClassProfile getThriftClientCallbackClassProfile()
	{
		ClassProfile newProfile = new ClassProfile();

		newProfile.setFullPath("X.1TW");
		newProfile.setKnownPath("");

		newProfile.setMinDepth(1);
		newProfile.setMaxDepth(1);
		newProfile.setModifiers(PUBLIC );

		newProfile.setTypeParamCount(0);
		newProfile.setSuperClass(	new ClassItem(java.lang.Object.class));
/////////////////////////
//Interfaces
/////////////////////////
		newProfile.setInterfaces(new ClassItem[]
				{
						new ClassItem("com.facebook.proxygen.MQTTClientCallback" , PUBLIC | INTERFACE | ABSTRACT | EXACT )

				});
/////////////////////////
//Nested Classes
/////////////////////////
		newProfile.setNestedClasses(new ClassItem[]
				{

				});
/////////////////////////
//Declared fields
/////////////////////////
		newProfile.setDeclaredFields(new FieldItem[]
				{
						new FieldItem( PUBLIC | EXACT , 	new ClassItem(java.util.concurrent.atomic.AtomicBoolean.class)),	//B
						new FieldItem( PUBLIC | FINAL | SYNTHETIC | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//C

				});
/////////////////////////
//Declared Methods
/////////////////////////
		newProfile.setDeclaredMethods(new MethodProfile[]
				{
						//Method #0: onConnectFailure
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class),

										new ClassItem("com.facebook.proxygen.MQTTClientError" , PUBLIC | EXACT )

								),

						//Method #1: onConnectSent
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class)

								),

						//Method #2: onConnectSuccess
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class),

										new ClassItem(byte[].class)

								),

						//Method #3: onError
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class),

										new ClassItem("com.facebook.proxygen.MQTTClientError" , PUBLIC | EXACT )

								),

						//Method #4: onPingRequest
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class)

								),

						//Method #5: onPingRequestFailure
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class),

										new ClassItem("com.facebook.proxygen.MQTTClientError" , PUBLIC | EXACT )

								),

						//Method #6: onPingRequestSent
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class)

								),

						//Method #7: onPingResponse
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class)

								),

						//Method #8: onPingResponseFailure
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class),

										new ClassItem("com.facebook.proxygen.MQTTClientError" , PUBLIC | EXACT )

								),

						//Method #9: onPublish
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class),

										new ClassItem(java.lang.String.class),
										new ClassItem(byte[].class),
										new ClassItem(int.class),
										new ClassItem(int.class)

								),

						//Method #10: onPublishAck
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class),

										new ClassItem(int.class)

								),

						//Method #11: onPublishAckFailure
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class),

										new ClassItem("com.facebook.proxygen.MQTTClientError" , PUBLIC | EXACT )

								),

						//Method #12: onPublishFailure
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class),

										new ClassItem(int.class),
										new ClassItem("com.facebook.proxygen.MQTTClientError" , PUBLIC | EXACT )

								),

						//Method #13: onPublishSent
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class),

										new ClassItem(java.lang.String.class),
										new ClassItem(int.class)

								),

						//Method #14: onSubscribeAck
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class),

										new ClassItem(int.class)

								),

						//Method #15: onSubscribeFailure
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class),

										new ClassItem(int.class),
										new ClassItem("com.facebook.proxygen.MQTTClientError" , PUBLIC | EXACT )

								),

						//Method #16: onUnsubscribeAck
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class),

										new ClassItem(int.class)

								),

						//Method #17: onUnsubscribeFailure
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class),

										new ClassItem(int.class),
										new ClassItem("com.facebook.proxygen.MQTTClientError" , PUBLIC | EXACT )

								),


				});
/////////////////////////
//Declared Constructors
/////////////////////////
		newProfile.setDeclaredConstructors(new ConstructorProfile[]
				{
						//Constructor #0
						new ConstructorProfile
								(		PUBLIC | EXACT ,

										new ClassItem("" , PUBLIC | EXACT )

								),


				});

		return newProfile;
	}


	public static ClassProfile getConnectRunnableClassProfile()
	{
		ClassProfile newProfile = new ClassProfile();

		newProfile.setFullPath("X.1U3");
		newProfile.setKnownPath("");

		newProfile.setMinDepth(1);
		newProfile.setMaxDepth(1);
		newProfile.setModifiers(PUBLIC );

		newProfile.setTypeParamCount(0);
		newProfile.setSuperClass(	new ClassItem(java.lang.Object.class));
/////////////////////////
//Interfaces
/////////////////////////
		newProfile.setInterfaces(new ClassItem[]
				{
						new ClassItem(java.lang.Runnable.class)

				});
/////////////////////////
//Nested Classes
/////////////////////////
		newProfile.setNestedClasses(new ClassItem[]
				{

				});
/////////////////////////
//Declared fields
/////////////////////////
		newProfile.setDeclaredFields(new FieldItem[]
				{
						new FieldItem( PUBLIC | FINAL | SYNTHETIC | EXACT , 	new ClassItem("" , PUBLIC | EXACT )),	//B
						new FieldItem( PUBLIC | FINAL | SYNTHETIC | EXACT , 	new ClassItem(java.lang.String.class)),	//C
						new FieldItem( PUBLIC | FINAL | SYNTHETIC | EXACT , 	new ClassItem(int.class)),	//D
						new FieldItem( PUBLIC | FINAL | SYNTHETIC | EXACT , 	new ClassItem(int.class)),	//E
						new FieldItem( PUBLIC | FINAL | SYNTHETIC | EXACT , 	new ClassItem(int.class)),	//F
						new FieldItem( PUBLIC | FINAL | SYNTHETIC | EXACT , 	new ClassItem(boolean.class)),	//G
						new FieldItem( PUBLIC | FINAL | SYNTHETIC | EXACT , 	new ClassItem(byte[].class)),	//H
						new FieldItem( PUBLIC | STATIC | FINAL | EXACT , 	new ClassItem(java.lang.String.class)),	//__redex_internal_original_name

				});
/////////////////////////
//Declared Methods
/////////////////////////
		newProfile.setDeclaredMethods(new MethodProfile[]
				{
						//Method #0: run
						new MethodProfile
								(
										PUBLIC | FINAL | EXACT ,
										new ClassItem(void.class)

								),


				});
/////////////////////////
//Declared Constructors
/////////////////////////
		newProfile.setDeclaredConstructors(new ConstructorProfile[]
				{
						//Constructor #0
						new ConstructorProfile
								(		PUBLIC | EXACT ,

										new ClassItem("" , PUBLIC | EXACT ),
										new ClassItem(java.lang.String.class),
										new ClassItem(int.class),
										new ClassItem(byte[].class),
										new ClassItem(int.class),
										new ClassItem(int.class),
										new ClassItem(boolean.class)

								),


				});

		return newProfile;
	}
}