package ca.ulaval.glo4002.trading;

import ca.ulaval.glo4002.trading.interfaces.configuration.Context;
import ca.ulaval.glo4002.trading.interfaces.rest.filters.EntityManagerContextFilter;

import ca.ulaval.glo4002.trading.interfaces.rest.mappers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;

import java.time.LocalDate;
import java.util.EnumSet;


public class TradingServer implements Runnable {
    private static final int PORT = 8181;
    private static final String APPLICATION_PACKAGE = "ca.ulaval.glo4002.trading";
    private static final String CONTEXT_ROOT = "/";
    private static final String CONTEXT_SPEC = CONTEXT_ROOT + "*";

    public static void main(String[] _args) {
        new TradingServer().run();
    }

    public void run() {
        ObjectMapper mapper = new ObjectMapper();
        Server server = new Server(PORT);
        ServletContextHandler contextHandler = new ServletContextHandler(server, CONTEXT_ROOT);
        contextHandler.addFilter(EntityManagerContextFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        LocalDate appExecutionContextDate = LocalDate.now();
        Context context = new Context(mapper, appExecutionContextDate);
        ResourceConfig packageConfig = new ResourceConfig()
                .packages(APPLICATION_PACKAGE)
                .registerInstances(context.createAccountResource())
                .registerInstances(context.createTransactionResource())
                .registerInstances(context.createReportResource())
                .registerInstances(new ClientAPIExceptionMapper())
                .registerInstances(new AccountAlreadyOpenExceptionMapper())
                .registerInstances(new AccountInvalidAmountExceptionMapper())
                .registerInstances(new TransactionDtoInvalidExceptionMapper())
                .registerInstances(new StockDtoInvalidExceptionMapper())
                .registerInstances(new AccountRequestInvalidExceptionMapper())
                .registerInstances(new InvalidDateExceptionMapper())
                .registerInstances(new InvalidQuantityExceptionMapper())
                .registerInstances(new InvalidTransactionNumberExceptionMapper())
                .registerInstances(new NotEnoughCreditExceptionMapper())
                .registerInstances(new NotEnoughStockExceptionMapper())
                .registerInstances(new StockNotFoundExceptionMapper())
                .registerInstances(new MarketIsClosedExceptionMapper())
                .registerInstances(new StockParametersDontMatchExceptionMapper())
                .registerInstances(new AccountNotFoundExceptionMapper())
                .registerInstances(new TransactionNotFoundExceptionMapper())
                .registerInstances(new UnsupportedTransactionExceptionMapper())
                .registerInstances(new StockDateNotFoundExceptionMapper())
                .registerInstances(new TransactionReportMissingDateExceptionMapper())
                .registerInstances(new TransactionReportMissingTypeExceptionMapper())
                .registerInstances(new TransactionReportInvalidDateExceptionMapper())
                .registerInstances(new TransactionReportTypeUnsupportedExceptionMapper());
        ServletContainer container = new ServletContainer(packageConfig);
        ServletHolder servletHolder = new ServletHolder(container);

        contextHandler.addServlet(servletHolder, CONTEXT_SPEC);

        try {
            server.start();
            server.join();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            server.destroy();
        }
    }
}
