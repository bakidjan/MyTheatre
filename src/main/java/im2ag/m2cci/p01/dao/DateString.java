package im2ag.m2cci.p01.dao;

import java.util.Date;
import java.text.ParseException;
import java.lang.IllegalArgumentException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class DateString {
  private Date date; /* format date is YYYY-MM-DD */
  private final static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private final static DateFormat dateFormatDisplayed = new SimpleDateFormat("dd/MM/yyyy") ;
  private final static String usage = "Mauvais format de date (" + DateString.dateFormat + ").";

  /* constructeur avec date du jour */
  public DateString () {
    this.date = new Date();
  }

  public DateString (String date) throws IllegalArgumentException {
    try {
      this.date = DateString.dateFormat.parse(date);
    } catch (ParseException e) {
      throw new IllegalArgumentException(DateString.usage);
    }
  }

  /* test if date1 > date2 */
  public boolean after (String date) throws IllegalArgumentException {
    boolean isAfter = false;
    try {
      isAfter = this.date.after(DateString.dateFormat.parse(date));
    } catch (ParseException e) {
      throw new IllegalArgumentException(DateString.usage);
    }
    return isAfter;
  }

  public Date getDate () {
    return this.date;
  }

  public String getYear () {
    DateFormat format = new SimpleDateFormat("yyyy");
    return format.format(this.date);
  }

  public String getMonth () {
    DateFormat format = new SimpleDateFormat("MM");
    return format.format(this.date);
  }

  public String getDateString () {
    return DateString.dateFormat.format(this.date);
  }

  @Override
  public String toString () {
    return DateString.dateFormatDisplayed.format(this.date);
  }
}
