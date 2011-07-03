/*
 [The "BSD license"]
 Copyright (c) 2011 Terence Parr
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.antlr.v4.codegen.model;

import org.antlr.v4.codegen.OutputModelFactory;
import org.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.tool.GrammarAST;

import java.util.*;

/** */
public abstract class LL1Loop extends Choice {
	@ModelElement public OutputModelObject loopExpr;
	@ModelElement public List<SrcOp> iteration;
	@ModelElement public Sync sync;

	public LL1Loop(OutputModelFactory factory,
				   GrammarAST blkAST,
				   List<CodeBlockForAlt> alts)
	{
		super(factory, blkAST, alts);
	}

	public void addIterationOp(SrcOp op) {
		if ( iteration==null ) iteration = new ArrayList<SrcOp>();
		iteration.add(op);
	}

	public SrcOp addCodeForLoopLookaheadTempVar(IntervalSet look) {
		SrcOp expr = addCodeForLookaheadTempVar(look);
		if ( expr instanceof TestSetInline ) {
			TestSetInline e = (TestSetInline)expr;
			CaptureNextTokenType nextType = new CaptureNextTokenType(e.varName);
			addIterationOp(nextType);
		}
		return expr;
	}
}
