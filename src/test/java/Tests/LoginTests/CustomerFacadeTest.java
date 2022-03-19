package Tests.LoginTests;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Util.TablePrinterUtil;
import DB.DatabaseInitializer;
import DB.Util.DBManager;
import DB.Util.DBTools;
import Exceptions.EntityAlreadyExistException;
import Exceptions.EntityCrudException;
import Facades.AdminFacade;
import Facades.CompanyFacade;
import LoginManager.ClientType;
import LoginManager.LoginManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class CustomerFacadeTest {
    public static AdminFacade adminFacade;
    public static CompanyFacade[] companyFacade;
    public static Company macrohard;
    public static Company banana;
    public static int couponIdCounter;
    public static int companyIdCounter;
    public Coupon[] databaseMacrohardCoupons;
    public Coupon[] expectedMacrohardCoupons;
    public Coupon[] databaseBananaCoupons;
    public Coupon[] expectedBananaCoupons;
    public static Consumer<Coupon> couponAssertion = expected -> {
        try {
            Coupon actual = companyFacade[companyIdCounter - 1].readCouponById(couponIdCounter);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getCompanyId(), actual.getCompanyId());
            assertEquals(expected.getAmount(), actual.getAmount());
            assertEquals(expected.getPrice(), actual.getPrice(), 0);
            assertEquals(expected.getCategory(), actual.getCategory());
            assertEquals(expected.getTitle(), actual.getTitle());
            assertEquals(expected.getDescription(), actual.getDescription());
            assertEquals(expected.getStartDate(), actual.getStartDate());
            assertEquals(expected.getEndDate(), actual.getEndDate());
            couponIdCounter++;
        } catch (EntityCrudException e) {
            System.out.println(e.getMessage());
        }
    };

    @Before
    public void initiation() throws EntityAlreadyExistException, EntityCrudException {
        // Database set-up:
        DatabaseInitializer.createTables();
        // Array set-ups:
        companyFacade = new CompanyFacade[2];
        databaseMacrohardCoupons = new Coupon[2];
        expectedMacrohardCoupons = new Coupon[2];
        databaseBananaCoupons = new Coupon[1];
        expectedBananaCoupons = new Coupon[1];
        // Company creation and login:
        adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.valueOf("ADMINISTRATOR"));
        macrohard = new Company("Macrohard Corporation", "MacroBusiness@coldmail.com", "secretlyMicrosoft");
        banana = new Company("Banana Inc", "Banana.Business@bmail.com", "betterthanmacrohard");
        adminFacade.addCompany(macrohard);
        adminFacade.addCompany(banana);
        companyFacade[0] = (CompanyFacade) LoginManager.getInstance().login("MacroBusiness@coldmail.com", "secretlyMicrosoft", ClientType.COMPANY);
        companyFacade[1] = (CompanyFacade) LoginManager.getInstance().login("Banana.Business@bmail.com", "betterthanmacrohard", ClientType.COMPANY);
        // idCounter Set-up:
        couponIdCounter = 1;
        companyIdCounter = 1;
        // Coupon creation:
        databaseMacrohardCoupons[0] = new Coupon(
                1
                , 2
                , 399.99
                , Category.ELECTRICITY
                , "Macrohard PC Coupon"
                , "0.1% off on new Macrohard computers!"
                , "gloogle-generic-computer.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(20))
        );
        expectedMacrohardCoupons[0] = new Coupon(
                1
                , 1
                , 2
                , 399.99
                , Category.ELECTRICITY
                , "Macrohard PC Coupon"
                , "0.1% off on new Macrohard computers!"
                , "gloogle-generic-computer.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(20))
        );
        databaseMacrohardCoupons[1] = new Coupon(
                1
                , 2
                , 49.99
                , Category.SOFTWARE
                , "Macrohard Doors OS Coupon"
                , "0.01% off on new Macrohard OS!"
                , "doors-os-logo.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(13))
        );
        expectedMacrohardCoupons[1] = new Coupon(
                2
                , 1
                , 2
                , 49.99
                , Category.SOFTWARE
                , "Macrohard Doors OS Coupon"
                , "0.01% off on new Macrohard OS!"
                , "doors-os-logo.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(13))
        );
        databaseBananaCoupons[0] = new Coupon(
                2
                , 3
                , 999.99
                , Category.SOFTWARE
                , "Banana OS Coupon"
                , "0.001% off on new Banana OS!"
                , "banana-os-logo.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(10))
        );
        expectedBananaCoupons[0] = new Coupon(
                3,
                2
                , 3
                , 999.99
                , Category.SOFTWARE
                , "Banana OS Coupon"
                , "0.001% off on new Banana OS!"
                , "banana-os-logo.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(10))
        );
    }

    @After
    public void finish() throws SQLException {
        System.out.println("Dropped schema: " + DBTools.runQuery(DBManager.DROP_SCHEMA));
    }

    //========================================

    @Test
    public void getCustomerDetailsTest() throws Exception {

    }
//    @Test
//    public void getCompanyDetailsTest() throws Exception {// todo example
//        Company expected = macrohard;
//        Company actual = companyFacade[0].getCompanyDetails();
//        assertEquals(companyIdCounter, actual.getId());
//        assertEquals(expected.getName(), actual.getName());
//        assertEquals(expected.getEmail(), actual.getEmail());
//    }
//
//    @Test
//    public void printCompanyDetailsTest() throws Exception {
//        companyFacade[0].addCoupon(macrohardCoupons[0]);
//        companyFacade[0].addCoupon(macrohardCoupons[2]);
//        TablePrinterUtil.print(companyFacade[0].getCompanyDetails());
//    }
}
