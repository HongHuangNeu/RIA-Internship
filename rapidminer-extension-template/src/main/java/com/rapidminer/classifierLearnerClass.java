package com.rapidminer;
import com.rapidminer.example.Attribute;
import com.rapidminer.example.ExampleSet;
import com.rapidminer.operator.Model;
import com.rapidminer.operator.OperatorCapability;
import com.rapidminer.operator.OperatorDescription;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.learner.PredictionModel;
import com.rapidminer.operator.learner.AbstractLearner;
public class classifierLearnerClass extends AbstractLearner  {

	public classifierLearnerClass(OperatorDescription description) {
		super(description);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Model learn(ExampleSet arg0) throws OperatorException {
		// TODO Auto-generated method stub
		return new classifierClass(arg0);
	}

	@Override
	public boolean supportsCapability(OperatorCapability arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Class<? extends PredictionModel> getModelClass() {
	//TODO: Needs to unify models in order to return common class
	return super.getModelClass();
	}

	

}
