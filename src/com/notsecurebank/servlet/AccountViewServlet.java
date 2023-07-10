package com.notsecurebank.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.notsecurebank.util.ServletUtil;

public class AccountViewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger(AccountViewServlet.class);

    public AccountViewServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("doGet");

        // show account balance for a particular account
        if (request.getRequestURL().toString().endsWith("showAccount")) {
            String accountName = request.getParameter("listAccounts");
            if (accountName == null) {
                response.sendRedirect(request.getContextPath() + "/bank/main.jsp");
                return;
            }
            LOG.info("Balance for accountName = '" + accountName + "'.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/bank/balance.jsp?acctId=" + accountName);
            dispatcher.forward(request, response);
            return;
        }
        // this shouldn't happen
        else if (request.getRequestURL().toString().endsWith("showTransactions"))
            doPost(request, response);
        else
            super.doGet(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("doPost");

        // show transactions within the specified date range (if any)
        if (request.getRequestURL().toString().endsWith("showTransactions")) {
            String startTime = request.getParameter("startDate");
            String endTime = request.getParameter("endDate");

            //V2
            //ho aggiunto dentro ServletUtil un metodo che controlla se la stringa è effetticamente una data
            //solo se entrambi gli input sono delle date allora viene stampato il log
            if(ServletUtil.isValidDate(startTime) && ServletUtil.isValidDate(endTime)){
                LOG.info("Transactions within '" + startTime + "' and '" + endTime + "'.");
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/bank/transaction.jsp?" + ((startTime != null) ? "&startTime=" + startTime : "") + ((endTime != null) ? "&endTime=" + endTime : ""));
            dispatcher.forward(request, response);
        }
    }
}
