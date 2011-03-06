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

package dasher.core;

import dasher.events.CEventHandler;
import dasher.settings.CSettingsStore;
import java.util.ArrayList;

/**
 * Stubbed logging class. These methods should be implemented
 * if we want to start generating a textual log.
 */
public class CUserLog {

	public CUserLog(CEventHandler EventHandler, CSettingsStore SetStore, int loglevel, CAlphabet Alpha) {
		//stub
	}
	
	public void OutputFile() {
		// stub
	}
	
	public void Close() {
		// stub
	}
	
	public void InitIsDone() {
		// stub
	}
	
	public void StopWriting(float x) {
		// stub
	}
	
	public void StartWriting() {
		// stub
	}
	
	public void DeleteSymbols (int deleted) {
		// stub
	}
	
	public void AddSymbols (ArrayList<CSymbolProb> added) {
		// stub
	}
	
	public void SetAlphabetPtr(CAlphabet alph) {
		// stub
	}
}