package com.rapidminer;

import com.rapidminer.example.Attribute;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.example.Example;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.learner.PredictionModel;
import java.util.*;
public class classifierClass extends PredictionModel{

	private HashMap countMap=new HashMap<Integer,Integer>();
	private int predictedLabel;
	private int labelIndex;
	protected classifierClass(ExampleSet trainingExampleSet) {
		super(trainingExampleSet);
		// TODO Auto-generated constructor stub
		Attribute label = trainingExampleSet.getAttributes().getLabel();
		for(Example e: trainingExampleSet)
		{ 
			int labelValue = (int) e.getValue(label);
			if(countMap.containsKey(labelValue))
			{
				countMap.put(labelValue, (int)countMap.get(labelValue)+1);
			}else{
				countMap.put(labelValue, 1);
			}
		}
		
		
		int count=-1;
		Iterator<Map.Entry<Integer, Integer>> entries = countMap.entrySet().iterator();
		while (entries.hasNext()) {
		    Map.Entry<Integer, Integer> entry = entries.next();
		    if(entry.getValue()>count)
		    {
		    	count=entry.getValue();
		    	predictedLabel=entry.getKey();
		    }
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2057788787586389661L;

	@Override
	public ExampleSet performPrediction(ExampleSet arg0, Attribute arg1)
			throws OperatorException {
		// TODO Auto-generated method stub
		for(Example e:arg0)
		{
			e.setValue(arg1, predictedLabel);
		}
		return arg0;
	}

}
