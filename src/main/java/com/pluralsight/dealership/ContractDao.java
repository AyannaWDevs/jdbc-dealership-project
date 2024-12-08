package com.pluralsight.dealership;

import java.util.List;

public interface ContractDao {
    boolean addContract(Contract contract);
    List<Contract> getAllContracts();
    Contract getContractById(int id);
    boolean updateContractDetails(int id, String details);
    boolean deleteContract(int id);
}
