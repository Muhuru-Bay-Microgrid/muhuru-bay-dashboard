package org.mbmg.mbmg;

import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class to parse our remote telemetry data.  Currently only parses an extract file.  May have to handle
 * UDP datagrams if we have to write our own listener
 */

@Component
public class Parser {

    /**
     * Parses a time string of the form: 'TM:1404120015'.
     * <p>
     * Note: Since we don't have timezone info we use LocalDateTime
     *
     * @param timeString
     * @return
     */
    public static LocalDateTime parseDate(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'TM:'yyMMddHHmm");
        return LocalDateTime.parse(timeString, formatter);

    }

    /**
     * Load the data from the file, transform into Record POJOs
     *
     * @throws Exception
     */
    public static List<Record> parseFile(String filePath) throws Exception {
        return Files.lines(Paths.get(filePath)).
                filter(s -> !s.isEmpty()).
                map(Parser::toRecord).
                collect(Collectors.toList());
    }

    /**
     * Extract record metadata, channel data, and make a Record
     *
     * @param line
     * @return
     */
    public static Record toRecord(String line) {
        String[] columns = line.split(";");
        String[] headers = columns[0].split(":");
        return new Record(Long.parseLong(headers[0]),
                headers[1],
                headers[2].split(",")[0],
                parseDate(columns[2]),
                Stream.of(Arrays.copyOfRange(columns, 1, columns.length - 1)).
                        collect(Collectors.
                                toMap(datumLine -> datumLine.split(":")[0],
                                        datumLine -> Double.parseDouble(datumLine.split(":")[1])))
        );
    }


}
