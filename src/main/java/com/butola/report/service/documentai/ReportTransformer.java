package com.butola.report.service.documentai;

import com.butola.report.data.mongo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReportTransformer {
    private static final Logger logger = LoggerFactory.getLogger(ReportTransformer.class);

    public Report transform(String responseData) {
        ObjectMapper objectMapper = new ObjectMapper();
        Report report = new Report();
        try {
            JsonNode rootNode = objectMapper.readTree(responseData);
            Property property = buildProperty(rootNode);
            ContributionsOfNonFinancialAssets contributionsOfNonFinancialAssets = buildContributionsOfNonFinancialAssets(rootNode);
            NetAssetsWithDonorRestrictions netAssetsWithDonorRestrictions = buildNetAssetsWithDonorRestrictions(rootNode);
            BoardDesignatedNetAssets boardDesignatedNetAssets = buildBoardDesignatedNetAssets(rootNode);
            Lease lease = buildLease(rootNode);
            CashFlow cashFlow = buildCashFlow(rootNode);
            Liquidity liquidity = buildLiquidity(rootNode);


            //   report.setCompanyName(companyName);
            //   report.setYear(year);
            report.setProperty(property);
            report.setContributionsOfNonFinancialAssets(contributionsOfNonFinancialAssets);
            report.setNetAssetsWithDonorRestrictions(netAssetsWithDonorRestrictions);
            report.setBoardDesignatedNetAssets(boardDesignatedNetAssets);
            report.setLease(lease);
            report.setCashFlow(cashFlow);
            report.setLiquidity(liquidity);
        } catch (JsonProcessingException e) {
            logger.error("Error parsing JSON: {}", e.getMessage(), e);
        }

        return report;
    }

    private Property buildProperty(JsonNode rootNode) {
        Property property = new Property();

        JsonNode propertyData = rootNode.path("Property").path("data");
        if (propertyData.isArray()) {
            for (JsonNode dataNode : propertyData) {
                String item = dataNode.path("item").asText();
                String value2022 = dataNode.path("2022").asText(); // Assuming 2022 is the current year

                switch (item) {
                    case "Furniture and Equipment":
                        property.setFurniture_And_Equipment(value2022);
                        break;
                    case "Less Accumulated Depreciation":
                        property.setLess_Accumulated_Depreciation(value2022);
                        break;
                    case "Total":
                        property.setTotal(value2022);
                        break;
                    default:
                        // Handle unknown items if necessary
                        break;
                }
            }
        }

        JsonNode descriptionExpense = rootNode.path("Property").path("depreciation_expense");
        property.setDepreciationExpense(descriptionExpense.path("2022").asText());
        return property;
    }

    private ContributionsOfNonFinancialAssets buildContributionsOfNonFinancialAssets(JsonNode rootNode) {
        JsonNode contributionsData = rootNode.path("Contribution of Nonfinancial Assets").path("data");
        ContributionsOfNonFinancialAssets contributions = new ContributionsOfNonFinancialAssets();

        if (contributionsData.isArray()) {
            for (JsonNode dataNode : contributionsData) {
                String item = dataNode.path("item").asText();
                String value2022 = dataNode.path("2022").asText(); // Assuming 2022 is the current year

                switch (item) {
                    case "Grants and Assistance":
                        contributions.setGrantsAndAssistance(value2022);
                        break;
                    case "Professional Fees":
                        contributions.setProfessionalFees(value2022);
                        break;
                    case "Total":
                        contributions.setTotal(value2022);
                        break;
                    default:
                        // Handle unknown items if necessary
                        break;
                }
            }
        }
        return contributions;
    }

    private NetAssetsWithDonorRestrictions buildNetAssetsWithDonorRestrictions(JsonNode rootNode) {
        NetAssetsWithDonorRestrictions netAssets = new NetAssetsWithDonorRestrictions();
        JsonNode restrictionsData = rootNode.path("Net Assets with Donor Restrictions").path("data");

        if (restrictionsData.isArray()) {
            for (JsonNode categoryNode : restrictionsData) {
                String category = categoryNode.path("category").asText();

                // Extract values from "Subject to Expenditures for a Specified Purpose"
                if (category.contains("Specified Purpose")) {
                    JsonNode items = categoryNode.path("items");
                    if (items.isArray()) {
                        for (JsonNode itemNode : items) {
                            String item = itemNode.path("item").asText();
                            String value2022 = itemNode.path("2022").asText(); // Assuming 2022 is the current year

                            switch (item) {
                                case "Climate":
                                    netAssets.setClimate(value2022);
                                    break;
                                case "Worker's Center":
                                    netAssets.setWorkersCenter(value2022);
                                    break;
                                case "Wellness and Economic Stability":
                                    netAssets.setWellnessAndEconomicStability(value2022);
                                    break;
                                case "Environmental Justice":
                                    netAssets.setEnvironmentalJustice(value2022);
                                    break;
                                case "Immigrant Worker Support":
                                    netAssets.setImmigrantWorkerSupport(value2022);
                                    break;
                                case "Tech Needs":
                                    netAssets.setTechNeeds(value2022);
                                    break;
                                case "Combat COVID":
                                    netAssets.setCombatCOVID(value2022);
                                    break;
                                case "Direct Lobbying":
                                    netAssets.setDirectLobbying(value2022);
                                    break;
                                default:
                                    // Handle unknown items if necessary
                                    break;
                            }
                        }
                    }
                }

                // Extract value for "Subsequent Fiscal Years"
                if (category.contains("Subsequent Fiscal Years")) {
                    JsonNode items = categoryNode.path("items");
                    if (items.isArray()) {
                        for (JsonNode itemNode : items) {
                            String item = itemNode.path("item").asText();
                            String value2022 = itemNode.path("2022").asText(); // Assuming 2022 is the current year
                            if (item.equals("Total")) {
                                netAssets.setSubsequentFiscalYears(value2022);
                            }
                        }
                    }
                }

                // Extract value for "Total Net Assets with Donor Restrictions"
                if (category.contains("Total Net Assets with Donor Restrictions")) {
                    JsonNode items = categoryNode.path("items");
                    if (items.isArray()) {
                        for (JsonNode itemNode : items) {
                            String item = itemNode.path("item").asText();
                            String value2022 = itemNode.path("2022").asText(); // Assuming 2022 is the current year
                            if (item.equals("Total")) {
                                netAssets.setTotalNetAssetsWithDonorRestrictions(value2022);
                            }
                        }
                    }
                }
            }
        }
        return netAssets;
    }

    private BoardDesignatedNetAssets buildBoardDesignatedNetAssets(JsonNode rootNode) {
        JsonNode boardData = rootNode.path("Board Designated Net Assets").path("data");
        BoardDesignatedNetAssets boardDesignatedNetAssets = new BoardDesignatedNetAssets();
        if (boardData.isArray()) {
            for (JsonNode dataNode : boardData) {
                String item = dataNode.path("item").asText();
                String value2022 = dataNode.path("2022").asText(); // Assuming 2022 is the current year

                if ("Operating Reserve".equals(item)) {
                    boardDesignatedNetAssets.setOperatingReserve(value2022);
                }
            }
        }
        return boardDesignatedNetAssets;
    }

    private Lease buildLease(JsonNode rootNoe) {

        return new Lease();
    }

    private CashFlow buildCashFlow(JsonNode rootNode) {
        CashFlow cashFlow = new CashFlow();
        JsonNode cashFlowData = rootNode.path("Cashflow").path("data");

        if (cashFlowData.isArray()) {
            for (JsonNode dataNode : cashFlowData) {
                String item = dataNode.path("item").asText();
                String value2021 = dataNode.path("2021").asText();
                String value2022 = dataNode.path("2022").asText();

                switch (item) {
                    case "Net Change in Operating Lease Activity":
                        cashFlow.setNetChangeInOperatingLeaseActivity(value2021);
                        break;
                    case "Loss on Sale of Property":
                        cashFlow.setLossOnSaleOfProperty(value2021);
                        break;
                    case "Accounts Payable":
                        cashFlow.setAccountsPayable(value2021);
                        break;
                    case "Accrued Expenses":
                        cashFlow.setAccruedExpenses(value2021);
                        break;
                    case "Grants and Contracts Receivable":
                        cashFlow.setGrantsAndContractsReceivable(value2021);
                        break;
                    case "Prepaid Expense":
                        cashFlow.setPrepaidExpenses(value2021);
                        break;
                    case "Total Adjustments":
                        cashFlow.setTotalAdjustments(value2021);
                        break;
                    default:
                        // Handle unknown items if necessary
                        break;
                }
            }
        }

        return cashFlow;
    }

    private Liquidity buildLiquidity(JsonNode rootNode) {
        Liquidity liquidity = new Liquidity();
        JsonNode liquidityData = rootNode.path("Liquidity and Availability").path("data");

        if (liquidityData.isArray()) {
            for (JsonNode dataNode : liquidityData) {
                String item = dataNode.path("item").asText();
                String currentYearValue = dataNode.path("2022").asText(); // Assuming 2022 is the current year

                switch (item) {
                    case "Cash":
                        liquidity.setCurrentYearCash(currentYearValue);
                        break;
                    case "Grants and Contracts Receivable":
                        liquidity.setCurrentYearGrantsAndContractsReceivable(currentYearValue);
                        break;
                    case "Total Financial Assets":
                        liquidity.setCurrentYearTotalFinancialAssets(currentYearValue);
                        break;
                    case "Total Assets with Donor Restrictions":
                        liquidity.setTotalAssetsWithDonorRestrictions(currentYearValue);
                        break;
                    case "Net Assets with Restrictions to be met within a year":
                        liquidity.setNetAssetsWithRestrictions(currentYearValue);
                        break;
                    case "Total assets not available to be used within one year":
                        liquidity.setTotalAssetsNotAvailable(currentYearValue);
                        break;
                    case "Financial assets available for general expenditures within one year":
                        liquidity.setFinancialAssetsAvailable(currentYearValue);
                        break;
                    default:
                        // Handle unknown items if necessary
                        break;
                }
            }
        }

        // Set the current year (assumed to be 2022)
        liquidity.setCurrentYear(2022);

        // Print the populated Liquidity object (or use it as needed)
        System.out.println("Populated Liquidity object:");
        System.out.println(liquidity);
        return liquidity;
    }
}
