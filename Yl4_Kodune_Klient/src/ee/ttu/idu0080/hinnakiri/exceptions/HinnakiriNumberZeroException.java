package ee.ttu.idu0080.hinnakiri.exceptions;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault(name = "HinnakiriNumberZeroFault")
public class HinnakiriNumberZeroException extends Exception {

	private final HinnakiriNumberZeroFault faultInfo;

	public HinnakiriNumberZeroException() {
		this("Number is zero.");
	}

	public HinnakiriNumberZeroException(String message) {
		super(message);

		this.faultInfo = new HinnakiriNumberZeroFault();
		this.faultInfo.setMessage(message);
	}

	public HinnakiriNumberZeroException(String message, HinnakiriNumberZeroFault faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
	}

	public HinnakiriNumberZeroFault getFaultInfo() {
		return faultInfo;
	}
}
