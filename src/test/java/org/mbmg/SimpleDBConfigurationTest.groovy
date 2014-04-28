package org.mbmg

import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import spock.lang.Specification

/**
 * Created by rpomeroy on 4/26/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SimpleDBConfiguration.class)
class SimpleDBConfigurationTest extends Specification {


    void setup() {

    }

    void cleanup() {

    }

    def "SimpleDBTemplate1"() {

    }

    def "Repository"() {

    }
}
