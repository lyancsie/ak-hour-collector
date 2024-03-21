package org.lyancsie;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class PrivateLessonHtmlExtractorStrategy implements LessonHtmlExtractorStrategy<PrivateLesson>{
    @Override
    public Set<PrivateLesson> extract(String lessonHtml) {
        Set<PrivateLesson> lessons = new HashSet<>();
        Document doc = Jsoup.parse(lessonHtml);
        //group lesson: get(4), private lesson: first
        Element table = doc.select("table").first();
        Elements rows = Objects.requireNonNull(table).select("tr");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        for (Element row : rows) {
            Elements cols = row.select("td");
            if (cols.size() == 4) {
                try {
                    final var lesson = new PrivateLesson(
                        cols.get(0).text(),
                        LocalDate.parse(cols.get(1).text(), formatter),
                        Double.parseDouble(cols.get(2).text().split(" ")[0]),
                        cols.get(3).text());
                    lessons.add(lesson);
                } catch (Exception e) {
                    log.error(
                        """
                               Error parsing lesson. Columns: {}
                               Error: {}
                            """,
                        cols.toString(), e.toString());
                    break;
                }
            } else {
                log.warn("Invalid row: " + cols);
            }

        }
        return lessons;
    }
}
