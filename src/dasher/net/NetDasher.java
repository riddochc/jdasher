/*
  This file is part of JDasher.

  JDasher is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation; either version 2 of the License, or
  (at your option) any later version.

  JDasher is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with JDasher; if not, write to the Free Software
  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

  Copyright (C) 2006      Christopher Smowton <cs448@cam.ac.uk>

  JDasher is a port derived from the Dasher project; for information on
  the project see www.dasher.org.uk; for information on JDasher itself
  and related projects see www.smowton.net/chris

*/

package dasher.net;

import dasher.events.EventManager;
import dasher.resources.ResourceManager;
import dasher.settings.SettingsManager;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.ArrayList;

import java.io.File;
import java.io.FileFilter;

import dasher.core.CAlphIO;
import dasher.core.CColourIO;


/**
 * Implementation of DasherInterfaceBase designed for use as part
 * of a server supplying a remote interface to Dasher, typically
 * by a web front-end.
 * <p>
 * For specification details of the front end, see the information
 * relating to JSDasher at www.smowton.net/chris (found under Dasher Project).
 * <p>
 * As this implementation of Dasher is designed to be instantiated
 * once for each connection, it includes hooks for some repetitive
 * work to be externalised; specifically, it expects both a CAlphIO
 * and CColourIO to have been ready-constructed by its host.
 */
public class NetDasher extends dasher.core.CDasherInterfaceBase {
	
	/**
	 * Reference to the NetInput device which is feeding Dasher
	 * co-ordinates.
	 */
	protected NetInput m_CoordInput;
	
	/**
	 * Reference to our host
	 */
	protected DasherEditListener m_Host;
	
	/**
	 * User data location; this is where Dasher should look
	 * for training texts.
	 */
	protected String userDataLocation;
	
	/**
	 * Reference to the AlphIO object handed in by the host
	 */
	protected dasher.core.CAlphIO m_alphIO;
	
	/**
	 * Reference to the ColourIO object handed in by the host
	 */
	protected dasher.core.CColourIO m_ColourIO;
	
	/**
	 * Creates a new NetDasher which passes events out to a given
	 * host, and which gets its alphabets and colour schemes from
	 * given sources.
	 * <p>
	 * This constructor will call CreateSettingsStore and register
	 * an instance of NetInput as our input device; however Realize
	 * still has to be called before it is ready to be used.
	 * <p>
	 * See the superclass documentation for more detail on the
	 * Interface's role.
	 * 
	 * @param host Host to whom we should pass EditEvents
	 * @param alphIO AlphIO object to read alphabets from
	 * @param colIO ColourIO object to read colour schemes from
	 */
	public NetDasher(DasherEditListener host, dasher.core.CAlphIO alphIO, dasher.core.CColourIO colIO) {
		super();
		
		m_alphIO = alphIO;
		m_ColourIO = colIO;
		m_Host = host;
		CreateSettingsStore();
		m_CoordInput = new NetInput(m_EventHandler, m_SettingsStore);
		RegisterFactory(new dasher.core.CWrapperFactory(m_EventHandler, m_SettingsStore, m_CoordInput));
		CreateInput();
	}

	/**
	 * Informs the helper NetInput class that when next asked,
	 * it should supply these co-ordinates.
	 * 
	 * @param x Mouse x co-ordinate
	 * @param y Mouse y co-ordinate
	 */
	public void setCoordinates(int x, int y) {
		m_CoordInput.setNextCoords(x, y);
	}
	
	public void CreateSettingsStore() {
		
		m_SettingsStore = new dasher.settings.CSettingsStore(m_EventHandler);
		
	}
	
	/**
	 * Supplies the AlphIO object which was supplied to our
	 * constructor as a source of alphabet information.
	 */
	public CAlphIO doAlphIO(ArrayList<String> vFiles) {
		return m_alphIO;
	}

	/**
	 * Supplies the ColourIO object which was supplied to our
	 * constructor as a source of colour scheme information.
	 */
	public CColourIO doColourIO(ArrayList<String> vFiles) {
		return m_ColourIO;
	}

	public void ExternalEventHandler(dasher.events.CEvent event) {
		
		if(event.m_iEventType == 2) { // Edit event
			if(m_Host != null) {
				m_Host.HandleEvent((dasher.events.CEditEvent)event);
			}
		}
		
	}
	
	public int GetFileSize(String strFileName) {
		java.io.File theFile = new java.io.File(strFileName);
		return (int)theFile.length();
	}

	
	public void Redraw(boolean bChanged) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Stub; not required in this version of Dasher since our
	 * host is responsible for constructing a CAlphIO
	 */
	public void ScanAlphabetFiles(Collection<String> vFileList) {

		// Stub: shouldn't getSetting used in this version of Dasher.
		
	}

	/**
	 * Stub; not required in this version of Dasher since our
	 * host is responsible for constructing a CColourIO
	 */
	public void ScanColourFiles(Collection<String> vFileList) {

		// Stub: shouldn't getSetting used in this version of Dasher.
		
	}

	public void SetupPaths() {

		SetStringParameter(dasher.settings.Esp_parameters.SP_USER_LOC, userDataLocation);
		
	}
	
	/**
	 * Sets the location where Dasher should search for training
	 * texts.
	 * 
	 * @param loc New data location
	 */
	public void setUserLoc(String loc) {
		userDataLocation = loc;
	}

	
	public void SetupUI() {
		// TODO Auto-generated method stub
		
	}

    // <editor-fold defaultstate="collapsed" desc="DasherSession impl">
    // TODO: DasherSession impl for NetDasher
    @Override
    public Object getHost() {
        return null;
    }

    public SettingsManager getSettingsManager() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ResourceManager getResourcesManager() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public EventManager getEventManager() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public File getWorkingFile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setWorkingFile(File file) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isPaused() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setPaused(boolean isPaused) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isLocked() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setLock(boolean isLocked, String reason) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setLock(boolean isLocked) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }// </editor-fold>

    public String getLockReason() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
/**
 * Simple factory for the production of FileFilters which return
 * files whose names match a given regular expression.
 */
class FilterFactory {
	
	/**
	 * Produces a new FileFilter which accepts
	 * files whose names match a given regular expression.
	 * 
	 * @param regex Regular expression to match
	 * @return FileFilter class which filters for this regex.
	 */
	public static FileFilter makeFilter(String regex) {
		
		final String target = regex;
		
		return new java.io.FileFilter() {
			public boolean accept (File file) {
				return file.getName().matches(target);
			}
		};
	}
	
}
