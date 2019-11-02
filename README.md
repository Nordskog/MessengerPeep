This module is for use with Messenger's Android app ( com.facebook.orca )
It dumps information on sent and received MQTT messages into logcat with the log tag "###".
The current release targets 239.1.0.17.119

For anything we send we can call the toString() on the object before it's encoded,  
giving us human-readable output.  

Incoming messages are a bit more difficult. We can recreate the structure,  
but do not have access to the field names.  
However, nearly all incoming messages have outgoing equivalents,  
meaning if we can check if the incoming message has field id/types that  
match the outgoing ones.   

The more fields the more likely we are to end up with the right one.  
Below we start off with the toString() of the connect message object,  
and also parse the raw byte message that is sent with the same name-guessing-scheme  
as for incoming packets. Since there are a ton of fields, these are all accurate. 

```
I: ############################
I: #####  ENCODING OBJECT  ####
I: ############################
I:  
I: ConnectMessage (
      clientIdentifier : "85ecf301-f00a-4c46-8",
      clientInfo : ClientInfo (
        userId : 100035440333556,
        userAgent : "[FBAN/Orca-Android;FBAV/209.0.0.17.98;]",
        clientCapabilities : 17335,
        endpointCapabilities : 90,
        publishFormat : ZLIB_OPTIONAL (2),
        noAutomaticForeground : true,
        makeUserAvailableInForeground : true,
        isInitiallyForeground : false,
        networkType : 1,
        networkSubtype : 0,
        clientMqttSessionId : 115905325,
        subscribeTopics : [
          155
          107
          150
          174
          34
          59
          131
          75
          90
          62
          98
          72
          85
          86
          63
        ],
        clientType : "",
        connectTokenHash : DE 90 F3 8F 0A 26 E6 BC F0 60 E7 CB 6B 70 00 F3,
        regionPreference : "ATN",
        deviceSecret : "",
        clientStack : 4,
        networkTypeInfo : WIFI (7)
      ),
      combinedPublishes : [
      ],
      phpOverride : PhpTierOverrideHostPort (
        port : 0
      )
    )
I: Bytes: 181438356563663330312D663030612D346334362D383C16E8CB968EEABE2D18275B4642414E2F4F7263612D416E64726F69643B464241562F3230392E302E302E31372E39383B5D16EE8E0216B40115041111221502150016DACCC46E29F50FB602D601AC02DC02447686029601B4017CC4019001AA01AC017E18003810DE90F38F0A26E6BCF060E7CB6B7000F3180341544E18001304650E00490C3C25000000
I:  
I:  
I: ############################
I: ####### CONNECT START ######
I: ############################
I:  
I:  
I: url: edge-mqtt.facebook.com
I: Bytes: 181438356563663330312D663030612D346334362D383C16E8CB968EEABE2D18275B4642414E2F4F7263612D416E64726F69643B464241562F3230392E302E302E31372E39383B5D16EE8E0216B40115041111221502150016DACCC46E29F50FB602D601AC02DC02447686029601B4017CC4019001AA01AC017E18003810DE90F38F0A26E6BCF060E7CB6B7000F3180341544E18001304650E00490C3C25000000
I:  
I: Potential matches: [Score: 0.5, Score: 0.5, Score: 0.25, Score: 0.25, Score: 0.25]
I: Potential matches: [Score: 1.0, Score: 0.33333334, Score: 0.2777778, Score: 0.2777778, Score: 0.2777778]
I: Potential matches: [Score: 1.0, Score: 1.0, Score: 1.0, Score: 1.0, Score: 1.0]
I: Object
    {
        #1 clientIdentifier ( string ): "85ecf301-f00a-4c46-8"
        #4 clientInfo ( struct ): Object
        {
            #1 userId ( i64 ): 100035440333556
            #2 userAgent ( string ): "[FBAN/Orca-Android;FBAV/209.0.0.17.98;]"
            #3 clientCapabilities ( i64 ): 17335
            #4 endpointCapabilities ( i64 ): 90
            #5 publishFormat ( i32 ): 2
            #6 noAutomaticForeground ( bool ): true
            #7 makeUserAvailableInForeground ( bool ): true
            #9 isInitiallyForeground ( bool ): false
            #10 networkType ( i32 ): 1
            #11 networkSubtype ( i32 ): 0
            #12 clientMqttSessionId ( i64 ): 115905325
            #14 subscribeTopics ( List of i32 ): 
                [155, 107, 150, 174, 34, 59, 131, 75, 90, 62, 98, 72, 85, 86, 63]
            #15 clientType ( string ): ""
            #18 connectTokenHash ( string ): "ސ�
            &��`��kp���"
            #19 regionPreference ( string ): "ATN"
            #20 deviceSecret ( string ): ""
            #21 clientStack ( byte ): 4
            #27 networkTypeInfo ( i32 ): 7
            
        }
        
        #8 combinedPublishes ( List of struct ): 
            []
        #11 phpOverride ( struct ): Object
        {
            #2 port ( i32 ): 0
            
        }
        
        
    }
I:  
I: ############################
I: ######## CONNECT END #######
I: ############################
```

Example of encoding an object, and reading it back with the wrong field names.  
You'll need the field IDs for recreating the message though, names are an extract.  

```
I: ############################
I: #####  ENCODING OBJECT  ####
I: ############################
I:  
I: MqttThriftHeader (
    
    )
I: Bytes: 00
I:  
I:  
I: ############################
I: #####  ENCODING OBJECT  ####
I: ############################
I:  
I: ForegroundState (
      inForegroundApp : true,
      keepAliveTimeout : 300,
      subscribeTopics : [
        140
        195
        92
        103
        100
        65
      ],
      subscribeGenericTopics : [
        SubscribeGenericTopic (
            topicName : "/t_active_beeper",
            qualityOfService : 0
          )
      ]
    )
I: Bytes: 1125D804196598028603B801CE01C8018201191C18102F745F6163746976655F62656570657215000000
I:  
I:  
I: ############################
I: ####### PUBLISH START ######
I: ############################
I:  
I:  
I: Topic: /t_fs
I: Bytes: 001125D804196598028603B801CE01C8018201191C18102F745F6163746976655F62656570657215000000
I:  
I: Removing extra null at beginning
I: Potential matches: [Score: 1.0, Score: 0.5, Score: 0.5, Score: 0.5, Score: 0.5]
I: Potential matches: [Score: 1.0, Score: 1.0, Score: 1.0, Score: 1.0, Score: 1.0]
I: Object
    {
        #1 inForegroundApp ( bool ): true
        #3 keepAliveTimeout ( i32 ): 300
        #4 subscribeTopics ( List of i32 ): 
            [140, 195, 92, 103, 100, 65]
        #5 subscribeGenericTopics ( List of struct ): 
            [Object
            {
                #1 hostName ( string ): "/t_active_beeper"
                #2 port ( i32 ): 0
                
            }
            ]
        
    }
I:  
I: ############################
I: ######## PUBLISH END #######
I: ############################
```

And finally we receive a published message.  
As you can see by the potential matches, the first object ( top level ) is likely wrong.  
The second is probably correct.   

```
I: ############################
I: #### REC PUBLISH START  ####
I: ############################
I:  
I: Topic: /t_sp
I: Bytes: 0012191C16C4C49CCCEBBE2D15021680B8D0CD0B369480C0050000
I: Removing extra null at beginning
I: Potential matches: [Score: 1.0, Score: 1.0, Score: 0.5, Score: 0.5, Score: 0.5]
I: Potential matches: [Score: 1.0, Score: 0.75, Score: 0.75, Score: 0.5, Score: 0.5]
I: Object
    {
        #1 isIncrementalUpdate ( bool ): false
        #2 updates ( List of struct ): 
            [Object
            {
                #1 uid ( i64 ): 100035639611682
                #2 state ( i32 ): 1
                #3 lastActiveTimeSec ( i64 ): 1557794304
                #6 allCapabilities ( i64 ): 5767178
                
            }
            ]
        
    }
I:  
I: ############################
I: ####   REC PUBLISH END  ####
I: ############################
```

// This one looks right too
```
I: ############################
I: #### REC PUBLISH START  ####
I: ############################
I:  
I: Topic: /t_callability_resp
I: Bytes: 00191C16C4C49CCCEBBE2D11111115000000
I: Removing extra null at beginning
I: Potential matches: [Score: 1.0, Score: 1.0, Score: 1.0, Score: 1.0, Score: 1.0]
I: Potential matches: [Score: 1.0, Score: 0.8, Score: 0.6, Score: 0.6, Score: 0.6]
I: Object
    {
        #1 additional_contacts ( List of struct ): 
            [Object
            {
                #1 userId ( i64 ): 100035639611682
                #2 has_permission ( bool ): true
                #3 is_callable_mobile ( bool ): true
                #4 is_callable_webrtc ( bool ): true
                #5 reason_code ( i32 ): 0
                
            }
            ]
        
    }
I:  
I: ############################
I: ####   REC PUBLISH END  ####
I: ############################
```

And here's what a more complex one looks like, like receiving a message  
It's got the important bits.  

```
I: ############################
I: #### REC PUBLISH START  ####
I: ############################
I:  
I: Topic: /t_ms
I: Bytes: 00191C2C1C1C16C4C49CCCEBBE2D0018226D69642E2463414141414141776851395A7735526C43615671733950326A747A736416BAECB9BBB4BFFAACB50116C4C49CCCEBBE2D16D2E99FBDD65A11291814736F757263653A6D657373656E6765723A7765623200180568656C6C6F390C000016800716800700
I: Removing extra null at beginning
I: Potential matches: [Score: 1.0, Score: 1.0, Score: 0.6666667, Score: 0.6666667, Score: 0.6666667]
I: Potential matches: [Score: 0.0, Score: 0.0, Score: 0.0, Score: 0.0, Score: 0.0]
I: Potential matches: [Score: 0.33333334, Score: 0.33333334, Score: 0.33333334, Score: 0.33333334, Score: 0.33333334]
I: Potential matches: [Score: 0.75, Score: 0.5, Score: 0.5, Score: 0.375, Score: 0.375]
I: Potential matches: [Score: 1.0, Score: 1.0, Score: 1.0, Score: 1.0, Score: 1.0]
I: Object
    {
        #1 deltas ( List of struct ): 
            [Object
            {
                #2 ( struct ): Object
                {
                    #1 ( struct ): Object
                    {
                        #1 threadKey ( struct ): Object
                        {
                            #1 userId ( i64 ): 100035639611682
                            
                        }
                        
                        #2 messageId ( string ): "mid.$cAAAAAAwhQ9Zw5RlCaVqs9P2jtzsd"
                        #3 offlineThreadingId ( i64 ): 6533866529618344733
                        #4 actorFbId ( i64 ): 100035639611682
                        #5 timestamp ( i64 ): 1557795175017
                        #6 shouldBuzzDevice ( bool ): true
                        #8 tags ( List of string ): 
                            [source:messenger:web]
                        #11 ( bool ): false
                        
                    }
                    
                    #2 willTopic ( string ): "hello"
                    #5 ( List of struct ): 
                        []
                    
                }
                
                
            }
            ]
        #2 firstDeltaSeqId ( i64 ): 448
        #3 lastIssuedSeqId ( i64 ): 448
        
    }
I:  
I: ############################
I: ####   REC PUBLISH END  ####
I: ############################
```
