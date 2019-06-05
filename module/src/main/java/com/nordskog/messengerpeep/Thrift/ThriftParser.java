package com.nordskog.messengerpeep.Thrift;

import android.util.Log;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TField;
import org.apache.thrift.protocol.TList;
import org.apache.thrift.protocol.TMap;
import org.apache.thrift.protocol.TSet;
import org.apache.thrift.protocol.TType;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ThriftParser
{

	private static final String LOGTAG = "###";

	private static Field TCompactProtocol_lastFieldId_field = null;

	static
	{
		try
		{
			TCompactProtocol_lastFieldId_field = TCompactProtocol.class.getDeclaredField( "lastFieldId_" );
			TCompactProtocol_lastFieldId_field.setAccessible(true);
		}
		catch  (Exception ex )
		{
			Log.e("###", ex.toString());
			ex.printStackTrace();
		}


	}

	public static List<ThriftObject> readConsecutiveStructure(TCompactProtocol prot)
	{
		ArrayList<ThriftObject> objects = new ArrayList<>();

		while (true)
		{
			ThriftObject object = readStructure(prot);

			if (object.mFields.isEmpty())
				break;

			objects.add(object);

			// Deltas are often used to keep track of field ids.
			// Multiple objects screw this up.
			// Reset here.
			try
			{
				TCompactProtocol_lastFieldId_field.set(prot, (short)0);
			}
			catch ( Exception ex )
			{
				Log.e("###", ex.toString());
				ex.printStackTrace();
			}


		}

		return objects;
	}

	public static ThriftObject readStructure(TCompactProtocol prot)
	{

		ThriftObject thriftObject = new ThriftObject();

		try
		{
			TField field;
			while (  ( field = prot.readFieldBegin() ).type != TType.STOP )
			{

				Object obj = null;

				switch( field.type )
				{
					case 2:
					case 3:
					case 4:
					case 6:
					case 8:
					case 10:
					case 11:
					case 12:
					{
						obj = getValue( field.type, prot );

						ThriftField newField = new ThriftField("", field.id, field.type, obj );
						thriftObject.addField(newField);

						break;
					}
					case 13:
					{

						TMap map = prot.readMapBegin();
						Map<Object, Object> newMap = new HashMap<>();

						if (map.size != 0)
						{
							for (int i = 0; i < map.size; i++)
							{
								Object key = getValue( map.keyType, prot );
								Object val = getValue( map.valueType, prot );

								newMap.put(key, val);
							}
						}

						ThriftField newField = new ThriftField("", field.id, field.type, newMap, map.keyType, map.valueType );
						thriftObject.addField(newField);

						prot.readMapEnd();

						break;
					}
					case 15:
					{

						TList list = prot.readListBegin();

						List<Object> newList = new ArrayList<>();

						if (list.size != 0)
						{
							for (int i = 0; i < list.size; i++)
							{
								Object val = getValue( list.elemType, prot );
								newList.add(val);
							}
						}

						ThriftField newField = new ThriftField("", field.id, field.type, newList, (byte) -1, list.elemType );
						thriftObject.addField(newField);


						prot.readListEnd();

						break;
					}
					case 14:
					{
						TSet set = prot.readSetBegin();

						Set<Object> newSet = new HashSet<>();

						if (set.size != 0)
						{
							for (int i = 0; i < set.size; i++)
							{
								Object val = getValue( set.elemType, prot );
								newSet.add(val);

							}
						}


						ThriftField newField = new ThriftField("", field.id, field.type, newSet, (byte) -1, set.elemType );
						thriftObject.addField(newField);

						prot.readMapEnd();

						break;
					}

				}

				prot.readFieldEnd();
			}
		}
		catch ( org.apache.thrift.transport.TTransportException ex )
		{
			// Expected because we can only check if stream contains more data by actually trying to read it.
		}
		catch ( Exception ex )
		{
			Log.i(LOGTAG, "proto exception");
			Log.i(LOGTAG, ex.toString());
			ex.printStackTrace();
		}

		return thriftObject;
	}

	public static String getTypeString(byte type)
	{

		switch( type )
		{
			case 2:
				return "bool";
			case 3:
				return "byte";
			case 4:
				return "double";
			case 6:
				return "i16";
			case 8:
				return "i32";
			case 10:
				return "i64";
			case 11:
				return "string";
			case 12:
				return "struct";
			case 13:
				return "map";
			case 14:
				return "set";
			case 15:
				return "list";
		}

		return String.valueOf(type);
	}

	public static Object getValue(byte type, TCompactProtocol prot )
	{
		try
		{
			switch( type )
			{
				case 2:
					return prot.readBool();
				case 3:
					return prot.readByte();
				case 4:
					return prot.readDouble();
				case 6:
					return prot.readI16();
				case 8:
					return prot.readI32();
				case 10:
					return prot.readI64();
				case 11:
				{
					ByteBuffer buff = prot.readBinary();

					byte[] bytes = new byte[buff.remaining()];
					buff.get(bytes, 0, buff.remaining());

					return bytes;

				}
				case 12:
				{
					prot.readStructBegin();

					ThriftObject struct = readStructure(prot);

					prot.readStructEnd();

					return struct;
				}

			}
		}
		catch ( Exception ex )
		{
			ex.printStackTrace();
			Log.i(LOGTAG, "get value problem");
		}

		return null;
	}
}
