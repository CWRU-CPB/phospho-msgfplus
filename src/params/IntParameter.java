package params;

public class IntParameter extends Parameter {

	protected IntParameter(String key, String name, String description,	boolean isOptional) {
		super(key, name, description, isOptional);
	}

	@Override
	public boolean parse(String value) {
		return false;
	}

	@Override
	public String getValueAsString() {
		// TODO Auto-generated method stub
		return null;
	}

}
