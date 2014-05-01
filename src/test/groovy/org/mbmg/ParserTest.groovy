package org.mbmg

import org.mbmg.mbmg.Parser
import spock.lang.Specification

import java.time.LocalDateTime

/**
 * Created by rpomeroy on 4/27/14.
 */
class ParserTest extends Specification {

    def "parsing a line should produce a record"() {
        setup:
        def line = "6:#STD:123456,511;L:308;TM:1404120025;D:1;T:01;C:46;A00:0.634;A01:00000;A02:00000;" +
                "A03:00000;A04:00000;A05:00000;A06:00000;A07:00000;A08:00000;A09:00000;A10:00000;A11:00000;" +
                "A12:00000;A13:22.31;A14:22.68;P01:00000000;P02:00000000;P03:00000000;P04:00000000;P05:00000000;" +
                "P06:00000000;K01:13300000000000000;O01:0000;2D#\n"
        when:
        def record = Parser.toRecord(line)
        then:
        record != null
        !record.getChannelData().isEmpty()
    }

    def "parsing the timestamp should produce a LocalDateTime"() {
        setup:
        def timeString = "TM:1404120015"
        when:
        def localDateTime = Parser.parseDate(timeString)
        then:
        localDateTime != null
        localDateTime.equals(LocalDateTime.of(2014, 4, 12, 0, 15))
    }

    def "parsing a file should produce a List of Records"() {
        setup:
        def filePath = "data/data_log.txt"
        when:
        def records = Parser.parseFile(filePath)
        then:
        records != null
        !records.isEmpty()
    }
}
