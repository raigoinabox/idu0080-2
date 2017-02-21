package ee.ttu.idu0080.hinnakiri.exceptions;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(name = "HinnakiriNumberNegativeFault")
public class HinnakiriNumberNegativeException extends Exception {
	private HinnakiriNumberNegativeFault faultInfo;

	public HinnakiriNumberNegativeException() {
		this("Number is negative.");
	}

	public HinnakiriNumberNegativeException(String message) {
		super(message);

		faultInfo = new HinnakiriNumberNegativeFault();
		faultInfo.setMessage(message);
	}

	public HinnakiriNumberNegativeException(String message, HinnakiriNumberNegativeFault faultInfo) {
		super(message);

		this.faultInfo = faultInfo;
	}

	public HinnakiriNumberNegativeFault getFaultInfo() {
		return faultInfo;
	}
}
