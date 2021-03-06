package com.rapidminer;
import com.rapidminer.example.*;
import com.rapidminer.example.table.AttributeFactory;
import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorDescription;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.operator.ports.InputPort;
import com.rapidminer.operator.ports.OutputPort;
import com.rapidminer.operator.ports.metadata.AttributeMetaData;
import com.rapidminer.operator.ports.metadata.ExampleSetMetaData;
import com.rapidminer.operator.ports.metadata.ExampleSetPassThroughRule;
import com.rapidminer.operator.ports.metadata.ExampleSetPrecondition;
import com.rapidminer.operator.ports.metadata.SetRelation;
import com.rapidminer.tools.Ontology;
public class testOperator extends Operator {
	//define input ports
	private InputPort exampleSetInput = getInputPorts().createPort("example set",ExampleSet.class); 
	
	//define output ports
	private OutputPort exampleSetOutput = getOutputPorts().createPort("example set"); 
	public testOperator(OperatorDescription description) {
		super(description);
		// TODO Auto-generated constructor stub
	
		//add precondition to test if the desired attribute is present in the data set	
		exampleSetInput.addPrecondition(new ExampleSetPrecondition(exampleSetInput,new String[]{"before"},Ontology.ATTRIBUTE_VALUE));
		
		//pass on the meta data from the input port to the output port, change the meta data if necessary
		getTransformer().addRule(new ExampleSetPassThroughRule(exampleSetInput,exampleSetOutput,SetRelation.EQUAL){
			@Override
			public ExampleSetMetaData modifyExampleSet(ExampleSetMetaData meta){
				//if changes to meta data is necessary, add them here
				
				//because we have renamed the attribute "before" to "after", we should change the meta data accordingly
				AttributeMetaData amd=meta.getAttributeByName("before");
				if(amd!=null){
					amd.setType(Ontology.INTEGER);
					amd.setName("after");
					amd.setValueSetRelation(SetRelation.UNKNOWN);
				}
				return meta;
			}
		});
	}
	
	
	/*
	 * Implement what the operator does:
	 * rename the name of the column, add a certain number to each value of this column
	 * */
	@Override
	public void doWork() throws OperatorException{
		ExampleSet exampleSet=exampleSetInput.getData(ExampleSet.class);
		
		//get all the columns in the table
		Attributes attributes=exampleSet.getAttributes();
		
		//fetch the target attribute
		Attribute sourceAttribute=attributes.get("before");
		
		/*
		 * To rename this attribute, we create a new attribute and call it "after"
		 * */
		Attribute targetAttribute=AttributeFactory.createAttribute("after",Ontology.INTEGER);
		
		//point this attribute to what attribute "before" points to
		targetAttribute.setTableIndex(sourceAttribute.getTableIndex());
		
		//add the new attribute, remove the original attribute
		attributes.addRegular(targetAttribute);
		attributes.remove(sourceAttribute);
		//rename complete
		
		
		int M=2;
		//start adding a certain value
		for(Example e:exampleSet){
			double value=e.getValue(targetAttribute);
			e.setValue(targetAttribute, value+M);
		}
		
		
		exampleSetOutput.deliver(exampleSet);
	}

}
