package prr;

import java.io.Serializable;
import java.io.IOException;
import prr.exceptions.UnrecognizedEntryException;

import java.io.FileReader;
import java.io.BufferedReader;

import java.util.Map;
import java.util.TreeMap;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	private int totalComms = 0;
	private Map<String, Client> clients = new TreeMap<String, Client>();
	private Map<String, Terminal> terminals = new TreeMap<String, Terminal>();
        // FIXME define attributes
        // FIXME define contructor(s)
        // FIXME define methods

	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param filename name of the text input file
     * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException if there is an IO erro while processing the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException, ClassNotFoundException /* FIXME maybe other exceptions */  {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				Object[] tokens = line.split("\\|");
				if (tokens[0].equals("CLIENT")) {
					// FIXME create client
				}
				else if (tokens[0].equals("BASIC")) {
					// FIXME create basic terminal
				}
				else if (tokens[0].equals("FANCY")) {
					// FIXME create fancy terminal
				}
				else if (tokens[0].equals("O2S")) {

				}
				else if (tokens[0].equals("O2I")) {

				}
				else if (tokens[0].equals("B2I")) {

				}
				else if (tokens[0].equals("S2I")) {

				}
				else if (tokens[0].equals("VOICE")) {

				}
				else if (tokens[0].equals("TEXT")) {

				}
				else if (tokens[0].equals("VIDEO")) {
					
				}
				else {
					throw new UnrecognizedEntryException(line);
				}
			}
		}
		catch (IOException e) {
			throw new IOException(filename);
		}
		catch (ClassNotFoundException e) {
			throw new ClassNotFoundException(filename);
		}
    }
}

