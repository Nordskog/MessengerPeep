package com.nordskog.messengerpeep.Thrift;

import android.support.annotation.Nullable;
import android.util.Log;

import com.nordskog.messengerpeep.Profiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ThriftSearcher
{
	/*
	public static void adoptNamesFromKnown( ThriftObject raw, ThriftObject template )
	{
		if (template != null)
		{
			raw.setFieldNamesFrom( template );
		}

	}
	*/

	public static ThriftObject findBestMatch( ThriftObject raw )
	{
		List<ThriftObject> candidates = findEqual(raw);

		if (!candidates.isEmpty())
			return candidates.get(0);

		return null;
	}

	public static void recursivelyMatchAndName(ThriftObject raw, @Nullable ThriftObject bestMatch )
	{
		if (raw == null)
			return;

		if (bestMatch == null)
			bestMatch = findBestMatch(raw);

		if (bestMatch != null)
		{
			raw.setFieldNamesFrom(bestMatch);
		}

		for (ThriftField field : raw.mFields)
		{

			if (field.isStructure() )
			{

				recursivelyMatchAndName(  (ThriftObject) field.object, null);

			}
			else if (field.isList() && field.valueElementType == 12)
			{
				List<ThriftObject> list = (List<ThriftObject>) field.object;
				if (!list.isEmpty())
				{
					ThriftObject child = list.get(0);
					ThriftObject bestChildMatch = findBestMatch( child );
					if (bestChildMatch != null)
					{
						for (ThriftObject object : list)
						{
							recursivelyMatchAndName(object, bestChildMatch);
						}
					}
				}
			}
			else if (field.isSet() && field.valueElementType == 12)
			{
				Set<ThriftObject> set = (Set<ThriftObject>) field.object;
				if (!set.isEmpty())
				{
					ThriftObject child = set.iterator().next();
					ThriftObject bestChildMatch = findBestMatch( child );
					if (bestChildMatch != null)
					{
						for (ThriftObject object : set)
						{
							recursivelyMatchAndName(object, bestChildMatch);
						}
					}
				}
			}
			else if (field.isMap() && field.valueElementType == 12)
			{
				Map<Object, ThriftObject> map = (Map<Object, ThriftObject>) field.object;
				if (!map.isEmpty())
				{
					ThriftObject child = map.values().iterator().next();
					ThriftObject bestChildMatch = findBestMatch( child );
					if (bestChildMatch != null)
					{
						for (ThriftObject object : map.values())
						{
							recursivelyMatchAndName(object, bestChildMatch);
						}
					}
				}
			}
		}
	}


	public static List<ThriftObject> findEqual(  ThriftObject raw )
	{
		List<CandidateHolder> candidates = new ArrayList<>();

		float minScore = -1;

		for (ThriftObject other : Profiles.parsedClasses)
		{
			float score = raw.getSimilarity(other);

			if ( score > minScore )
			{
				candidates.add( new CandidateHolder(other, score) );

				Collections.sort( candidates, (a, b) ->
				{
					// Note reverse order so it's descending
					return Float.compare(b.score, a.score);
				});

				if (candidates.size() > 5)
				{
					candidates.remove( candidates.size() - 1 );
					minScore = candidates.get( candidates.size() - 1 ).score;
				}

			}
		}

		Log.i("###", "Potential matches: "+candidates.toString());

		ArrayList<ThriftObject> returnCans = new ArrayList<ThriftObject>();
		for (CandidateHolder candidate : candidates)
		{
			returnCans.add(candidate.object);
		}

		return returnCans;
	}

	/*
	public static List<ThriftObject> searchAndDecimateEqual( @Nullable List<ThriftObject> list, ThriftObject target )
	{
		if ( list == null )
		{
			list = findEqual(target);
		}

		Iterator<ThriftObject> it = list.iterator();

		while (it.hasNext())
		{
			ThriftObject currentObj = it.next();
			if ( !target.isEqual(currentObj) )
			{
				it.remove();
			}
		}

		return list;
	}
	*/

	public static ThriftObject findForClass( Class clazz )
	{
		for (ThriftObject parsedClass : Profiles.parsedClasses)
		{
			if (parsedClass.mClass == clazz)
				return parsedClass;
		}

		return null;
	}

	private static class CandidateHolder
	{
		ThriftObject object;
		float score;

		public CandidateHolder( ThriftObject object, float score )
		{
			this.object = object;
			this.score = score;
		}

		@Override
		public String toString()
		{
			return "Score: "+this.score;
		}
	}



}
