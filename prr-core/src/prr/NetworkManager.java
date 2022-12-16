package prr;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import prr.exceptions.Cores_RepeatedFriendException;
import prr.exceptions.ImportFileException;
import prr.exceptions.MissingFileAssociationException;
import prr.exceptions.UnavailableFileException;
import prr.exceptions.UnrecognizedEntryException;

/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager {

	/** The network itself. */
	private Network _network = new Network();
	private String _filename = null;


    public Network getNetwork() {
		return _network;
	}

	/**
	 * @param filename name of the file containing the serialized application's state
         *        to load.
	 * @throws UnavailableFileException if the specified file does not exist or there is
         *         an error while processing this file.
	 */
	public void load(String filename) throws UnavailableFileException {
		try  (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
			_network = (Network) ois.readObject();
			_filename = filename;
		} catch (IOException e) {
			throw new UnavailableFileException(filename);
		} catch (ClassNotFoundException e) {
			throw new UnavailableFileException(filename);
		}
	}


	public void save() throws IOException, MissingFileAssociationException {
		if (_filename == null || _filename.isBlank()) {
			throw new MissingFileAssociationException();
		}

		ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)));
		oos.writeObject(_network);
		oos.close();
	}


	public void saveAs(String filename) throws IOException, MissingFileAssociationException {
		_filename = filename;
		save();
	}

	/**
	 * Read text input file and create domain entities..
	 * 
	 * @param filename name of the text input file
	 * @throws ImportFileException
	 * @throws Cores_RepeatedFriendException
	 */
	public void importFile(String filename) throws ImportFileException {
		try {
            _network.importFile(filename);
    	} catch (IOException | UnrecognizedEntryException  e) {
            throw new ImportFileException(filename, e);
		} catch (Cores_RepeatedFriendException e){

		}
	}
}
