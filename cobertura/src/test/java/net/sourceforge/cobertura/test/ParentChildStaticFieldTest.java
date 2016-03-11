/*
 * The Apache Software License, Version 1.1
 *
 * Copyright (C) 2000-2002 The Apache Software Foundation.  All rights
 * reserved.
 * Copyright (C) 2010 John Lewis
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "Ant" and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

package net.sourceforge.cobertura.test;

import groovy.util.AntBuilder;
import groovy.util.Node;
import net.sourceforge.cobertura.ant.ReportTask;
import net.sourceforge.cobertura.test.util.TestUtils;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.resources.FileResource;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ParentChildStaticFieldTest extends AbstractCoberturaTestCase {
	AntBuilder ant = TestUtils.getCoberturaAntBuilder(TestUtils
			.getCoberturaClassDir());

	@Test
	public void parentChildStaticFieldShouldWorkWithoutInstrumentalizationTest() throws Exception {
		/*
		 * Use a temporary directory and create a few sources files.
		 */
		File tempDir = TestUtils.getTempDir();
		File srcDir = new File(tempDir, "src");
		File instrumentDir = new File(tempDir, "instrument");

		File mainSourceFile = new File(srcDir, "mypackage/ParentChildStaticField.java");
		File datafile = new File(srcDir, "cobertura.ser");
		mainSourceFile.getParentFile().mkdirs();

		byte[] encoded =  Files.readAllBytes(Paths.get("src/test/resources/examples/basic/src/com/example/simple/ParentChildStaticFieldExample.java"));

		FileUtils.write(mainSourceFile, new String(encoded, "utf8"));

		TestUtils.compileSource(ant, srcDir);

		/*
		 * Kick off the Main (instrumented) class.
		 */
		Java java = new Java(); 
		java.setProject(TestUtils.project);
		java.setClassname("mypackage.ParentChildStaticField");
		java.setDir(srcDir);
		java.setFork(true);
		java.setFailonerror(true);
		java.setClasspath(TestUtils.getCoberturaDefaultClasspath());
		java.execute();
		

	}
@Test
	public void parentChildStaticFieldShouldWorkAfterInstrumentalizationTest() throws Exception {
		/*
		 * Use a temporary directory and create a few sources files.
		 */
		File tempDir = TestUtils.getTempDir();
		File srcDir = new File(tempDir, "src");
		File instrumentDir = new File(tempDir, "instrument");

		File mainSourceFile = new File(srcDir, "mypackage/ParentChildStaticField.java");
		File datafile = new File(srcDir, "cobertura.ser");
		mainSourceFile.getParentFile().mkdirs();

		byte[] encoded =  Files.readAllBytes(Paths.get("src/test/resources/examples/basic/src/com/example/simple/ParentChildStaticFieldExample.java"));

		FileUtils.write(mainSourceFile, new String(encoded, "utf8"));

		TestUtils.compileSource(ant, srcDir);

		TestUtils.instrumentClasses(ant, srcDir, datafile, instrumentDir);
		
		//moving instrumented classes so we don't have to change classpaths
		FileUtils.copyDirectory(instrumentDir, srcDir);
		
		/*
		 * Kick off the Main (instrumented) class.
		 */
		Java java = new Java(); 
		java.setProject(TestUtils.project);
		java.setClassname("mypackage.ParentChildStaticField");
		java.setDir(srcDir);
		java.setFork(true);
		java.setFailonerror(true);
		java.setClasspath(TestUtils.getCoberturaDefaultClasspath());
		java.execute();
		

	}}