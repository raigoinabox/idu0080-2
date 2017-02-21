package ee.ttu.idu0080.hinnakiri;

/**
 * Klient hinnakirja teenusele
 */

import java.io.File;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.WebServiceException;

import ee.ttu.idu0080.hinnakiri.exceptions.HinnakiriNumberDecimalException;
import ee.ttu.idu0080.hinnakiri.exceptions.HinnakiriNumberFormatException;
import ee.ttu.idu0080.hinnakiri.exceptions.HinnakiriNumberNegativeException;
import ee.ttu.idu0080.hinnakiri.exceptions.HinnakiriNumberZeroException;
import ee.ttu.idu0080.hinnakiri.service.HinnakiriService;
import ee.ttu.idu0080.hinnakiri.service.HinnakiriService_Service;
import ee.ttu.idu0080.hinnakiri.types.GetHinnakiriResponse;
import ee.ttu.idu0080.hinnakiri.types.Hinnakiri;
import ee.ttu.idu0080.hinnakiri.types.Hinnakiri.HinnakirjaRida;

public final class Klient {

	public static void main(String args[]) throws Exception {
		URL wsdlURL = parseArguments(args);

		String[] pricesToTry = { "R99.999", "12.00A", "12A", "12.34", "12.340", "12.345", "12.0", "-12.00", "0.000",
				"-0" };
		for (String price : pricesToTry) {
			int connectTriesLeft = 10;
			boolean success = false;
			while (connectTriesLeft > 0 && !success) {
				try {
					try {
						HinnakiriService_Service service = new HinnakiriService_Service(wsdlURL);
						HinnakiriService port = service.getHinnakiriPort();

						System.out.println("Proovin hinda " + price);
						GetHinnakiriResponse response = port.getHinnakiri(price);
						printToConsole(response.getHinnakiri());
					} catch (HinnakiriNumberFormatException e) {
						System.out.println("Viga: Hind ei ole numbrilises formaadis.");
					} catch (HinnakiriNumberNegativeException e) {
						System.out.println("Viga: Hind on väiksem kui null.");
					} catch (HinnakiriNumberZeroException e) {
						System.out.println("Viga: Hind on null.");
					} catch (HinnakiriNumberDecimalException e) {
						System.out.println("Viga: Hinna täpsus on rohkem kui kaks komakohta.");
					}
					success = true;
				} catch (WebServiceException e) {
					if (e.getCause() instanceof ConnectException) {
						connectTriesLeft--;
					} else {
						success = true;
						System.out.println(e.toString());
					}
				}

				if (connectTriesLeft <= 0) {
					System.out.println("Ühendumine serveriga ebaõnnestus.");
					return;
				}
			}
		}
	}

	/**
	 * Prindib konsoolile hinnakirja
	 * 
	 * @param hinnakiri
	 */
	private static void printToConsole(Hinnakiri hinnakiri) {

		System.out.println("Hinnakiri:");

		for (HinnakirjaRida hinnakirjaRida : hinnakiri.getHinnakirjaRida()) {
			StringBuilder outRida = new StringBuilder();
			outRida.append(hinnakirjaRida.getToode().getKood());
			outRida.append("\t");
			outRida.append(hinnakirjaRida.getToode().getNimetus());
			outRida.append("\t");
			outRida.append(hinnakirjaRida.getHind().getSumma());
			outRida.append(" ");
			outRida.append(hinnakirjaRida.getHind().getValuuta());

			System.out.println(outRida);
		}
	}

	/**
	 * Parsib välja programmi argumentide hulgast WSDL-i URL-i
	 * 
	 * @param args
	 *            argumendid
	 * @return URL
	 */
	private static URL parseArguments(String[] args) {

		URL wsdlURL = HinnakiriService_Service.WSDL_LOCATION;

		try {
			if (args.length > 0) {
				String firstArg = args[0];

				if (firstArg.startsWith("http:")) {
					wsdlURL = new URL(firstArg);
				} else {
					File wsdlFile = new File(firstArg);
					if (wsdlFile.exists()) {
						wsdlURL = wsdlFile.toURI().toURL();
					} else {
						wsdlURL = new URL(firstArg);
					}
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return wsdlURL;
	}

}
