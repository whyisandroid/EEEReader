/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 ******************************************************************************/

package com.ereader.reader.dampedspring;

import com.glview.libgdx.graphics.math.Vector3;


public class sDampedSpringPosition3 extends sDampedSpringPosition<Vector3> {	
	public sDampedSpringPosition3() {
		super();
		
		mvIdealPos   = new Vector3(0, 0, 0);
		mvCurrentPos = new Vector3(0, 0, 0);
		
		mApTem       = new Vector3(0, 0, 0);
		mAvTem       = new Vector3(0, 0, 0);
		
		
		mvVel = new Vector3(0, 0, 0);
		
		
		mOutPutTem = new Vector3(0, 0, 0);
		mVcpyTem = new Vector3(0, 0, 0);
	}
}