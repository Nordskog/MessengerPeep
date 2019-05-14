package com.nordskog.messengerpeep.Thrift;

import android.util.Log;

import com.mayulive.xposed.classhunter.ProfileHelpers;
import com.nordskog.messengerpeep.Profiles;
import com.nordskog.messengerpeep.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.nordskog.messengerpeep.StringUtils.INDENT;

public class ThriftObject
{
	public Class mClass = null;
	public String mName = "Object";
	public List<ThriftField> mFields = new ArrayList<>();
	int maxFieldId = 0;


	static boolean crashed = false;

	public ThriftObject()
	{

	}

	public void addField( ThriftField field )
	{
		mFields.add(field);
		if ( field.fieldId > maxFieldId )
			maxFieldId = field.fieldId;
	}

	public void setFieldNamesFrom( ThriftObject other )
	{
		for (ThriftField field : mFields)
		{
			if (other != null)
			{
				if (field.fieldId < 1)
					continue;

				if (field.fieldId > other.mFields.size())
					continue;

				ThriftField otherField = other.mFields.get(field.fieldId-1);

				if (otherField.type != field.type)
					continue;

				field.name = otherField.name;

				if (field.isStructure())
				{
					//ThriftObject fieldObject = (ThriftObject) field.object;
					//fieldObject.setFieldNamesFrom(otherField.valueElemntClass);
				}
			}

			/*
			else if (field.isStructure())
			{
				ThriftObject fieldObject = (ThriftObject) field.object;
				fieldObject.setFieldNamesFrom(null);
			}
			*/


			/*
			if (field.isList() && field.valueElementType == 12)
			{
				List objectList = (List)field.object;
				List<ThriftObject> finalCandidates = null;
				if (objectList.size() > 0)
				{
					for ( Object listObject : objectList)
					{
						finalCandidates = ThriftSearcher.searchAndDecimateEqual(finalCandidates, (ThriftObject) listObject);
					}
				}


				ThriftObject finalCan = null;
				if ( finalCandidates != null && !finalCandidates.isEmpty() && finalCandidates.size() < 3 )
				{
					finalCan = finalCandidates.get(0);
				}


				for (Object listObject : objectList)
				{
					ThriftObject listThriftObject = (ThriftObject) listObject;
					listThriftObject.setFieldNamesFrom(finalCan);

				}
			}
			*/

		}
	}

	public boolean isEqual( ThriftObject other )
	{
		if (other == null)
			return false;

		if (maxFieldId > other.maxFieldId)
			return false;

		if ( maxFieldId >= other.mFields.size())
			return false;

		for (ThriftField field : mFields)
		{
			if (field.fieldId < 1)
				return false;

			if (field.fieldId >= other.mFields.size())
				return false;

			ThriftField otherField = other.mFields.get(field.fieldId-1);

			if (!field.isEqual( otherField.fieldId, otherField.type )  )
				return false;


			if (field.isStructure())
			{
				ThriftObject fieldObject = (ThriftObject) field.object;
				fieldObject.isEqual(otherField.valueElemntClass);
			}
		}

		return true;
	}

	public float getSimilarity( ThriftObject other )
	{
		if (other == null)
			return 0;

		if (other.mFields.isEmpty())
			return 0;

		if (mFields.isEmpty())
			return 0;

		//if (maxFieldId > other.maxFieldId)
		//	return false;

		//if ( maxFieldId >= other.mFields.size())
		//	return false;

		float score = 0;

		for (ThriftField field : mFields)
		{
			if (field.fieldId < 1)
				continue;

			if (field.fieldId > other.mFields.size())
				continue;

			ThriftField otherField = other.mFields.get(field.fieldId-1);

			if ( field.isEqual( otherField.fieldId, otherField.type )  )
			{
				if (field.isStructure())
				{
					ThriftObject fieldObject = (ThriftObject) field.object;
					score += fieldObject.getSimilarity(otherField.valueElemntClass);
				}
				else
				{
					score += 1;
				}
			}
		}

		score /= mFields.size();

		return score;
	}

	public static ThriftObject fromClass( Class clazz )
	{

		ThriftObject object = new ThriftObject();

		object.mClass = clazz;

		try
		{
			List<Field> fields = ProfileHelpers.findAllDeclaredFieldsWithType(Profiles.FieldDefinitionClass, clazz);


			for (Field field : fields)
			{
				field.setAccessible(true);

				Object instance = field.get(null);

				short fieldId = Profiles.FieldDefinitionClass_fieldIdField.getShort(instance);
				byte type = Profiles.FieldDefinitionClass_typedField.getByte(instance);
				String name = ( String )Profiles.FieldDefinitionClass_nameIdField.get(instance);

				ThriftField fieldDefinition = new ThriftField( name, fieldId, type );

				object.addField(fieldDefinition);
			}
		}
		catch ( Exception ex )
		{

			Log.i("###", "Hit shit fan");
			crashed = true;
			ex.printStackTrace();

		}

		Collections.sort(object.mFields, (field1, field2) ->
		{
			if ( field1.fieldId == field2.fieldId )
				return 0;
			if ( field1.fieldId > field2.fieldId)
				return 1;
			return -1;

		} );

		/*
		if ( clazz.getName().contains("X.8f3") )
		{
			Log.i("###", " ########## HERE ###################################");
			Log.i("###", object.toString());
		}
		*/

		return object;
	}


	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append(mName);
		builder.append("\n");
		builder.append("{");
		builder.append("\n");

		StringBuilder innerBuilder = new StringBuilder();
		innerBuilder.append(INDENT);

		for (ThriftField field : mFields)
		{
			innerBuilder.append( field.toString() );
			innerBuilder.append("\n");
		}

		builder.append( StringUtils.addIndent( innerBuilder.toString() ) );

		builder.append( "\n}\n");

		return builder.toString();

	}
}
