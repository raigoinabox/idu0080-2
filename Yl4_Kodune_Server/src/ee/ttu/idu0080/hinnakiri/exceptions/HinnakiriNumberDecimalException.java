package ee.ttu.idu0080.hinnakiri.exceptions;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(name = "HinnakiriNumberDecimalFault")
public class HinnakiriNumberDecimalException extends Exception {

	private HinnakiriNumberDecimalFault faultInfo;

	public HinnakiriNumberDecimalException() {
		this("Number has more than two decimals.");
	}

	public HinnakiriNumberDecimalException(String message) {
		super(message);

		this.faultInfo = new HinnakiriNumberDecimalFault();
		this.faultInfo.setMessage(message);
	}

	public HinnakiriNumberDecimalException(String message, HinnakiriNumberDecimalFault faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}

	public HinnakiriNumberDecimalFault getFaultInfo() {
		return faultInfo;
	}
}