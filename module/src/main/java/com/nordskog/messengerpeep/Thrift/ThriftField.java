package com.nordskog.messengerpeep.Thrift;

import com.nordskog.messengerpeep.StringUtils;

import static com.nordskog.messengerpeep.StringUtils.INDENT;

public class ThriftField
{
	public String name;
	public short fieldId;
	public byte type;

	// For maps, lists, and sets
	public byte keyElementType;
	public byte valueElementType;

	//Value, basically. Primitive, ThriftObject, or map/set/list
	public Object object = null;

	// If generated from class
	public ThriftObject keyElementClass = null;
	public ThriftObject valueElemntClass = null;


	public ThriftField( String name, short fieldId, byte type )
	{
		this(name, fieldId, type, null);
	}

	public ThriftField( String name, short fieldId, byte type, Object object )
	{
		this.name = name;
		this.fieldId = fieldId;
		this.type = type;
		this.object = object;
	}

	public ThriftField( String name, short fieldId, byte type, Object object, byte keyElementType, byte valueElementType )
	{
		this.name = name;
		this.fieldId = fieldId;
		this.type = type;
		this.object = object;

		this.keyElementType = keyElementType;
		this.valueElementType = valueElementType;
	}

	public boolean isEqual( short fieldId, byte type )
	{
		return this.fieldId == fieldId && this.type == type;
	}

	public boolean isStructure()
	{
		return this.type == 12;
	}

	public boolean isSet()
	{
		return this.type == 14;
	}

	public boolean isMap()
	{
		return this.type == 13;
	}

	public boolean isList()
	{
		return this.type == 15;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		boolean identValue = false;

		switch (type)
		{

			case 14:
			{
				identValue = true;

				builder.append("#");
				builder.append(fieldId);
				builder.append(" ");

				if ( name != null && !name.isEmpty())
				{
					builder.append( name);
					builder.append(" ");
				}

				builder.append("( Set of ");
				builder.append( ThriftParser.getTypeString(valueElementType) );
				builder.append(" ): \n");


				break;
			}
			case 15:
			{
				identValue = true;

				builder.append("#");
				builder.append(fieldId);
				builder.append(" ");

				if ( name != null && !name.isEmpty())
				{
					builder.append( name);
					builder.append(" ");
				}

				builder.append("( List of ");
				builder.append( ThriftParser.getTypeString(valueElementType));
				builder.append(" ): \n");

				break;
			}
			case 13:
			{
				identValue = true;

				builder.append("#");
				builder.append(fieldId);
				builder.append(" ");

				if ( name != null && !name.isEmpty())
				{
					builder.append( name);
					builder.append(" ");
				}

				builder.append("( Map of ");
				builder.append( ThriftParser.getTypeString(keyElementType));
				builder.append(" : ");
				builder.append( ThriftParser.getTypeString(valueElementType));
				builder.append(" ): \n");

				break;
			}

			default:
			{
				builder.append("#");
				builder.append(fieldId);
				builder.append(" ");

				if ( name != null && !name.isEmpty())
				{
					builder.append( name);
					builder.append(" ");
				}

				builder.append("( ");
				builder.append( ThriftParser.getTypeString(type) );
				builder.append(" ): ");
			}
		}



		if (object == null )
			builder.append( "NULL");
		else
		{

			if ( identValue )
			{
				builder.append( INDENT + StringUtils.addIndent( object.toString() ) );
			}
			else if (type == 11)
			{
				builder.append("\"");
				builder.append( object.toString() );
				builder.append("\"");
			}
			else
			{
				builder.append( object.toString() );
			}
		}


		return builder.toString();

	}
}
