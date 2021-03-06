package org.tothought.data.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.tothought.data.entities.Degree;
import org.tothought.data.entities.DegreeDetail;
import org.tothought.data.repositories.utils.TestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:META-INF/test-context.xml"})
@Transactional
public class DegreeRepositoryTest {

	@Autowired
	private DegreeRepository repository;

	@Test
	public void findOneTest() {
		Degree degree = repository.findOne(1);
		assertNotNull(degree);
		assertEquals("Information Sciences and Technology", degree.getProgram());
	}
	
	@Test
	public void insertTagTest() {
		Degree degree = TestUtil.createDegree();
		DegreeDetail detail = TestUtil.createDegreeDetail();
		degree.getDegreeDetails().add(detail);
		
		repository.save(degree);

		Degree dbDegree = repository.findOne(degree.getDegreeId());
		assertNotNull(dbDegree);
		assertEquals(degree.getInstitution(), dbDegree.getInstitution());
		assertEquals(degree.getEmphasis(), dbDegree.getEmphasis());
		assertEquals(degree.getProgram(), dbDegree.getProgram());
		assertTrue(degree.getDegreeDetails().size() > 0);
	}

	@Test
	public void updateTagTest() {
		String program = "Basket Weaving Sciences";
		
		Degree degree = repository.findOne(1);
		DegreeDetail detail = TestUtil.createDegreeDetail();
		
		degree.getDegreeDetails().add(detail);
		degree.setProgram(program);

		Degree dbDegree = repository.findOne(degree.getDegreeId());
		assertNotNull(dbDegree);
		assertEquals(program, dbDegree.getProgram());
		assertTrue(dbDegree.getDegreeDetails().size() > 1);
	}

	@Test
	public void deleteTagTest() {
		Degree degree = repository.findOne(2);
		repository.delete(degree);

		Degree dbDegree = repository.findOne(degree.getDegreeId());
		assertNull(dbDegree);
	}
}
