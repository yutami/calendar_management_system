package actions;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import models.Report;
import models.WeekInfo;
import services.ReportService;

/**
 * トップページに関する処理を行うActionクラス
 *
 */
public class TopAction extends ActionBase {

    private ReportService service;

    private static final long serialVersionUID = 1L;

    private static final int SUN_NUM = 0;

    private static final int MON_NUM = 1;

    private static final int TUE_NUM = 2;

    private static final int WED_NUM = 3;

    private static final int THU_NUM = 4;

    private static final int FRI_NUM = 5;

    private static final int SAT_NUM = 6;

    /**
     *processメソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new ReportService();

        //メソッドを実行
        invoke();

        service.close();

    }

    /**
     *カレンダー画面を表示する
     */
    public void index() throws ServletException, IOException {

        //セッションからログイン中のユーザー情報を取得
        UserView loginUser = (UserView) getSessionScope(AttributeConst.LOGIN_EMP);

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        LocalDate today = LocalDate.now();
        LocalDate targetDay = null;
        int year = 0;
        int month = 0;
        String yearStr = request.getParameter("year");
        String monthStr = request.getParameter("month");

        if ((yearStr != null && !yearStr.equals("") )&&
                (monthStr != null && !monthStr.equals(""))) {
            // 年月のパラメータが含まれている場合
            year = toNumber(yearStr);
            month = toNumber(monthStr);
            targetDay = LocalDate.of(year, month, 1);
        } else {
            // 年月のパラメータが空の場合
            // 本日の年月を設定
            year = today.getYear();
            month = today.getMonthValue();
            targetDay = today;
        }


         LocalDate previousMonth = targetDay.minusMonths(1);//先月
         int previousYearNum = previousMonth.getYear();
         int previousMonthNum = previousMonth.getMonthValue();

        LocalDate nextMonth = targetDay.plusMonths(1);//翌月
        int nextYearNum = nextMonth.getYear();
        int nextMonthNum = nextMonth.getMonthValue();

        List<Report> reportList = getReport(loginUser, year, month);

        Map<Integer, ArrayList<Report>> reportMap = new HashMap<Integer, ArrayList<Report>>();

        for (int i = 0; i < reportList.size(); ++i) {

            Report report = reportList.get(i);

            // LocalDatetime型からDate型へ
            LocalDateTime reservlocalDateTime = report.getReserve_day();

            // 予約日付を取得
            int reserveDay = reservlocalDateTime.getDayOfMonth();

            ArrayList<Report> log;

            // reportMapにキーが登録されいるか判定
            if (reportMap.containsKey(reserveDay)) {
                // 既にキーが登録されている場合
                log = reportMap.get(reserveDay);
            } else {
                // キーが登録されていない場合
                log = new ArrayList<Report>();
            }

            log.add(report);
            reportMap.put(reserveDay, log);

        }

        // 週ごとのカレンダー情報を取得
        List<WeekInfo> weekList = getCalendarByWeeks(year, month, reportMap);

        putRequestScope(AttributeConst.TOP_YEAR, year); // 年
        putRequestScope(AttributeConst.TOP_MONTH, month); // 月
        putRequestScope(AttributeConst.TOP_WEEKLIST, weekList); // 週の情報
        putRequestScope(AttributeConst.TOP_PREVIOUS_YEAR, previousYearNum);
        putRequestScope(AttributeConst.TOP_PREVIOUS_MONTH, previousMonthNum);
        putRequestScope(AttributeConst.TOP_NEXT_YEAR, nextYearNum);
        putRequestScope(AttributeConst.TOP_NEXT_MONTH, nextMonthNum);

        //一覧画面を表示
        forward(ForwardConst.FW_TOP_INDEX);

    }

    private List<Report> getReport(UserView loginUser, int year, int month) {
        // 月初め日(日時)
        LocalDateTime firstDateTime = LocalDateTime.of(year, month, 1, 0, 0, 0);
        // 月初め日(日付)
        LocalDate firstDate = LocalDate.of(year, month, 1);

        // 月末日(日時)
        // 月初め日の月から見て最後の日を第三引数に設定することで月末日(日時)が取得できる
        LocalDateTime lastDateTime = LocalDateTime.of(year, month, firstDate.lengthOfMonth(), 23, 59, 59);

        List<Report> reportList = new ArrayList<Report>();
        ReportService reportService = new ReportService();
        reportList = reportService.getAll(loginUser, firstDateTime, lastDateTime);
        return reportList;
    }

    /**
     * 週ごとのカレンダー情報を取得.
     * @param year  取得対象の年
     * @param month 取得対象の月
     * @param customerMap カスタマー情報
     * @return 週ごとのカレンダー情報
     */

    private List<WeekInfo> getCalendarByWeeks(int year, int month, Map<Integer, ArrayList<Report>> reportMap) {

        //Calendarインスタンスの作成
        Calendar cal = Calendar.getInstance();

        //日付の設定
        cal.set(year, month - 1, 1);

        //曜日の取得
        int iweek = cal.get(Calendar.DAY_OF_WEEK);

        //最終日を取得
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        int maxdate = cal.get(Calendar.DATE);

        //日付部分
        int day = 0;

        List<WeekInfo> weekList = new ArrayList<WeekInfo>();

        for (int rowweek = 0; rowweek < 6; rowweek++) {
            WeekInfo weekInfo = new WeekInfo();
            // 日曜日
            if (SUN_NUM < iweek - 1 && rowweek == 0) {
                weekInfo.setSunDate(null);
            } else if (day < maxdate) {
                day++;
                weekInfo.setSunDate(day);

                // 日付に紐つくデータがあるか判定
                if (reportMap.containsKey(day)) {
                    // データがある場合
                    weekInfo.setSunDateReceive(reportMap.get(day));
                }
            }

            // 月曜日
            if (MON_NUM < iweek - 1 && rowweek == 0) {
                weekInfo.setMonDate(null);
            } else if (day < maxdate) {
                day++;
                weekInfo.setMonDate(day);

                // 日付に紐つくデータがあるか判定
                if (reportMap.containsKey(day)) {
                    // データがある場合
                    weekInfo.setMonDateReceive(reportMap.get(day));
                }
            }

            // 火曜日
            if (TUE_NUM < iweek - 1 && rowweek == 0) {
                weekInfo.setTueDate(null);
            } else if (day < maxdate) {
                day++;
                weekInfo.setTueDate(day);
                // 日付に紐つくデータがあるか判定
                if (reportMap.containsKey(day)) {
                    // データがある場合
                    weekInfo.setTueDateReceive(reportMap.get(day));
                }

            }

            // 水曜日
            if (WED_NUM < iweek - 1 && rowweek == 0) {
                weekInfo.setWebDate(null);
            } else if (day < maxdate) {
                day++;
                weekInfo.setWebDate(day);

                // 日付に紐つくデータがあるか判定
                if (reportMap.containsKey(day)) {
                    // データがある場合
                    weekInfo.setWebDateReceive(reportMap.get(day));
                }
            }

            // 木曜日
            if (THU_NUM < iweek - 1 && rowweek == 0) {
                weekInfo.setThuDate(null);
            } else if (day < maxdate) {
                day++;
                weekInfo.setThuDate(day);

                // 日付に紐つくデータがあるか判定
                if (reportMap.containsKey(day)) {
                    // データがある場合
                    weekInfo.setThuDateReceive(reportMap.get(day));
                }
            }

            // 金曜日
            if (FRI_NUM < iweek - 1 && rowweek == 0) {
                weekInfo.setFriDate(null);
            } else if (day < maxdate) {
                day++;
                weekInfo.setFriDate(day);

                // 日付に紐つくデータがあるか判定
                if (reportMap.containsKey(day)) {
                    // データがある場合
                    weekInfo.setFriDateReceive(reportMap.get(day));
                }
            }

            // 土曜日
            if (SAT_NUM < iweek - 1 && rowweek == 0) {
                weekInfo.setSatDate(null);
            } else if (day < maxdate) {
                day++;
                weekInfo.setSatDate(day);

                // 日付に紐つくデータがあるか判定
                if (reportMap.containsKey(day)) {
                    // データがある場合
                    weekInfo.setSatDateReceive(reportMap.get(day));
                }

            }
            weekList.add(weekInfo);
        }
        return weekList;
    }

}
