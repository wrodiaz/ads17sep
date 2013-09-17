/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.telcomp.logic;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author enfasis2
 */
@WebService(serviceName = "JobSearchWebService")
public class JobSearchWebService {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "getJobOffer")
    public String getJobOffer(@WebParam(name = "keyword") String keyword) {
        return JobSearch.getJobOffers(keyword);
    }
}
