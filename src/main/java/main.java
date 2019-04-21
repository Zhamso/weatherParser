import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class main {
    private static Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");

    private static String getDateFromString(String str) throws Exception {
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Не могу найти дату в строке");
    }

    public static void main(String[] args) throws Exception {
        Document page = Jsoup.connect("http://pogoda.spb.ru").get();
        Element tableWth = page.select("table[class=wt]").first();
        Elements names = tableWth.select("tr[class=wth]");
        Elements values = tableWth.select("tr[valign=top]");
        System.out.println("Погода в СПБ");
        int index = 0;
        for (Element name : names) {
            String date = getDateFromString(name.select("th[id=dt]").text());
            System.out.println(date + "    Явления    Температура    Давление    Влажность    Ветер");
            int iterationCount = 4;
            if (index == 0) {
                if(values.get(0).select("td[width=80]").first().text().equals("Утро")) {
                    iterationCount = 4;
                }
                if(values.get(0).select("td[width=80]").first().text().equals("День")) {
                    iterationCount = 3;
                }
                if(values.get(0).select("td[width=80]").first().text().equals("Вечер")) {
                    iterationCount = 2;
                }
                if(values.get(0).select("td[width=80]").first().text().equals("Ночь")) {
                    iterationCount = 1;
                }
            } else {
                iterationCount = 4;
            }
            for (int i = 0 + index; i < iterationCount + index; i++) {
                System.out.println(values.get(i).text());
            }
            index += iterationCount;
            System.out.println();
        }
    }
}