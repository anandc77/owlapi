/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 12-Oct-2008<br><br>
 */
@SuppressWarnings("javadoc")
public class EquivalentClassesAxiomTestCase extends AbstractOWLAPITestCase {

    @Test
    public void testContainsNamedClass() {
        OWLClass clsA = getOWLClass("A");
        OWLClass clsB = getOWLClass("B");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLClassExpression desc = getFactory().getOWLObjectSomeValuesFrom(propP, clsB);
        OWLClassExpression desc2 = getFactory().getOWLObjectSomeValuesFrom(propP, clsA);
        OWLEquivalentClassesAxiom ax = getFactory().getOWLEquivalentClassesAxiom(clsA, desc);
        assertTrue(ax.containsNamedEquivalentClass());
        OWLEquivalentClassesAxiom ax2 = getFactory().getOWLEquivalentClassesAxiom(desc, desc2);
        assertFalse(ax2.containsNamedEquivalentClass());
    }


    @Test
    public void testGetNamedClasses() {
        OWLClass clsA = getOWLClass("A");
        OWLClass clsB = getOWLClass("B");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLClassExpression desc = getFactory().getOWLObjectSomeValuesFrom(propP, clsB);
        OWLEquivalentClassesAxiom ax = getFactory().getOWLEquivalentClassesAxiom(clsA, desc);
        Set<OWLClass> clses = ax.getNamedClasses();
        assertEquals(clses.size(), 1);
        assertTrue(clses.contains(clsA));
    }

    @Test
    public void testGetNamedClassesWithNothing() {
        OWLClass clsB = getOWLClass("B");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLClassExpression desc = getFactory().getOWLObjectSomeValuesFrom(propP, clsB);
        OWLEquivalentClassesAxiom ax = getFactory().getOWLEquivalentClassesAxiom(getFactory().getOWLNothing(), desc);
        Set<OWLClass> clses = ax.getNamedClasses();
        assertTrue(clses.isEmpty());
        assertFalse(ax.containsOWLThing());
        assertTrue(ax.containsOWLNothing());
    }

    @Test
    public void testGetNamedClassesWithThing() {
        OWLClass clsB = getOWLClass("B");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLClassExpression desc = getFactory().getOWLObjectSomeValuesFrom(propP, clsB);
        OWLEquivalentClassesAxiom ax = getFactory().getOWLEquivalentClassesAxiom(getFactory().getOWLThing(), desc);
        Set<OWLClass> clses = ax.getNamedClasses();
        assertTrue(clses.isEmpty());
        assertFalse(ax.containsOWLNothing());
        assertTrue(ax.containsOWLThing());
    }

    @Test
    public void testSplit() {
        OWLClass clsA = getOWLClass("A");
        OWLClass clsB = getOWLClass("B");
        OWLClass clsC = getOWLClass("C");
        Set<OWLClass> clses = new HashSet<OWLClass>();
        clses.add(clsA);
        clses.add(clsB);
        clses.add(clsC);
        OWLEquivalentClassesAxiom ax = getFactory().getOWLEquivalentClassesAxiom(clses);
        Set<OWLSubClassOfAxiom> scas = ax.asOWLSubClassOfAxioms();
        assertEquals(scas.size(), 6);
        assertTrue(scas.contains(getFactory().getOWLSubClassOfAxiom(clsA, clsB)));
        assertTrue(scas.contains(getFactory().getOWLSubClassOfAxiom(clsB, clsA)));
        assertTrue(scas.contains(getFactory().getOWLSubClassOfAxiom(clsA, clsC)));
        assertTrue(scas.contains(getFactory().getOWLSubClassOfAxiom(clsC, clsA)));
        assertTrue(scas.contains(getFactory().getOWLSubClassOfAxiom(clsB, clsC)));
        assertTrue(scas.contains(getFactory().getOWLSubClassOfAxiom(clsC, clsB)));
    }
}