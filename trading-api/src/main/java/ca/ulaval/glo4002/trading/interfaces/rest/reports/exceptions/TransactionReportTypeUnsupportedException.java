package ca.ulaval.glo4002.trading.interfaces.rest.reports.exceptions;

public class TransactionReportTypeUnsupportedException extends RuntimeException {
    private String reportType;

    public TransactionReportTypeUnsupportedException(String _reportType) {
        reportType = _reportType;
    }

    public String getReportType() {
        return reportType;
    }
}
