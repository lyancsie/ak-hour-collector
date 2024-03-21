package org.lyancsie.extractor;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lyancsie.lesson.GroupLesson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class GroupLessonHtmlExtractorStrategy implements LessonHtmlExtractorStrategy<GroupLesson> {
    @Override
    public Set<GroupLesson> extract(String lessonHtml) {

        Set<GroupLesson> lessons = new HashSet<>();
        Document doc = Jsoup.parse(lessonHtml);
        Element table = doc.select("table").get(4);
        log.debug("Table: {}", table);
        Elements rows = Objects.requireNonNull(table).select("tr");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        for (Element row : rows) {
            Elements cols = row.select("td");
            if (cols.size() == 13) {
                try {
                    log.info("Cols.size: {}", cols.size());
                    final var lesson = new GroupLesson(
                        LocalDate.parse(cols.get(1).text(), formatter),
                        Double.parseDouble(cols.get(3).text().split(" ")[0])
                    );
                    lessons.add(lesson);
                } catch (Exception e) {
                    log.error(
                        """
                               Error parsing lesson. Columns: {}
                               Error: {}
                            """,
                        cols, e.toString());
                }
            }
             else {
                log.warn("Invalid row: " + cols);
            }
        }
        return lessons;
    }
}