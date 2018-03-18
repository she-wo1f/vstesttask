package com.datalore.functesting.tests;

import com.datalore.functesting.pages.ConfirmationDialog;
import com.datalore.functesting.pages.LibraryActionReport;
import com.datalore.functesting.pages.LibraryManagerPage;
import com.datalore.functesting.pages.LibraryPackageDetails;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

public class LibraryManagerTest extends DataloreTest {

    LibraryManagerPage libraryManagerPage;

    @Before
    public void setUp(){
        workBookPage = homePage.createNewWorkBook(testName.getMethodName())
                .openWorkBook(testName.getMethodName());
        libraryManagerPage = workBookPage.openLibraryManager();

        validatePrecondition("Library Manager failed to open", libraryManagerPage.isInitialized(), is(true));
    }

    @After
    public void tearDown(){
        workBookPage.close();
        homePage.deleteWorkBook(testName.getMethodName());
    }

    @Test
    public void removePackage(){
        libraryManagerPage.showAllInstalledPackages();
        LibraryPackageDetails packageDetails = libraryManagerPage.openPackageDetails(0);
        String packageName = packageDetails.getPackageName();
        packageDetails.removePackage();
        LibraryActionReport removalReport = packageDetails.getActionReport();

        validateResult("Report window title assertion failed", removalReport.getReportActionName(), equalTo("removing"));
        validateResult("Report result assertion failed", removalReport.getReportActionResult(), equalTo("Installation complete"));

        removalReport.close();

        validateResult("Install button is not displayed", packageDetails.isInstallButtonDisplayed(), is(true));
        validateResult("Remove button is still displayed", packageDetails.isRemoveButtonDisplayed(), is(false));

        libraryManagerPage.showAllInstalledPackages();

        validateResult("Removed package is still displayed", libraryManagerPage.isInstalledPackage(packageName), is(false));

        libraryManagerPage.close();
        ConfirmationDialog confirmationDialog = workBookPage.getConfirmationDialog();

        validateResult("Text assertion in confirmation dialog failed", confirmationDialog.getDialogText(), containsString("Some packages were installed/removed, you need to restart kernel."));

        confirmationDialog.sendCancel();
    }

    @Test
    public void updatePackage(){
        libraryManagerPage.showAllInstalledPackages();
        LibraryPackageDetails packageDetails = libraryManagerPage.openPackageDetails(0);

        validatePrecondition("No available version for update", packageDetails.canBeUpdated(), is(true));

        packageDetails.updatePackage();
        String newPackageVersion = packageDetails.getVersion();
        String newPackageSource = packageDetails.getSource();
        LibraryActionReport updateReport = packageDetails.getActionReport();

        validateResult("Report window title assertion failed", updateReport.getReportActionName(), equalTo("updating"));
        validateResult("Report result assertion failed", updateReport.getReportActionResult(), equalTo("Installation complete"));

        updateReport.close();

        validateResult("Update button is still displayed", packageDetails.isUpdateButtonDisplayed(), is(false));
        validateResult("Remove button is not displayed", packageDetails.isRemoveButtonDisplayed(), is(true));

        libraryManagerPage.showAllInstalledPackages();

        validateResult("Package version assertion failed", libraryManagerPage.getPackageVersion(0), equalTo(newPackageVersion));
        validateResult("Package source assertion failed", libraryManagerPage.getPackageSource(0), equalTo(newPackageSource));

        libraryManagerPage.close();
        ConfirmationDialog confirmationDialog = workBookPage.getConfirmationDialog();

        validateResult("Text assertion in confirmation dialog failed", confirmationDialog.getDialogText(), containsString("Some packages were installed/removed, you need to restart kernel."));

        confirmationDialog.sendCancel();
    }

    @Test
    public void searchForInstalledPackage(){
        libraryManagerPage.showAllInstalledPackages();
        String packageName = libraryManagerPage.getPackageName(0);
        LibraryPackageDetails packageDetails = libraryManagerPage.searchForPackage(packageName);

        validateResult("Found package name assertion failed", packageDetails.getPackageName(), equalTo(packageName));
        validateResult("Remove button is not displayed", packageDetails.isRemoveButtonDisplayed(), is(true));

        libraryManagerPage.close();
    }

    @Test
    public void searchForNewPackageAndInstall(){
        String newPackageName = "abcd";

        validatePrecondition(newPackageName + " is already installed", libraryManagerPage.isInstalledPackage(newPackageName), is(false));

        LibraryPackageDetails packageDetails = libraryManagerPage.searchForPackage(newPackageName);
        packageDetails.installPackage();
        LibraryActionReport installationReport = packageDetails.getActionReport();

        validateResult("Report window title assertion failed", installationReport.getReportActionName(), equalTo("installing"));
        validateResult("Report result assertion failed", installationReport.getReportActionResult(), equalTo("Installation complete"));

        installationReport.close();

        validateResult("Install button is still displayed", packageDetails.isInstallButtonDisplayed(), is(false));
        validateResult("Remove button is not displayed", packageDetails.isRemoveButtonDisplayed(), is(true));

        libraryManagerPage.showAllInstalledPackages();

        validateResult("Installed package is not displayed", libraryManagerPage.isInstalledPackage(newPackageName), is(true));

        int packageIndex = libraryManagerPage.getPackageIndex(newPackageName);

        validateResult("Package version assertion failed", libraryManagerPage.getPackageVersion(packageIndex), equalTo(packageDetails.getVersion()));
        validateResult("Package source assertion failed", libraryManagerPage.getPackageSource(packageIndex), equalTo(packageDetails.getSource()));

        libraryManagerPage.close();
        ConfirmationDialog confirmationDialog = workBookPage.getConfirmationDialog();

        validateResult("Text assertion in confirmation dialog failed", confirmationDialog.getDialogText(), containsString("Some packages were installed/removed, you need to restart kernel."));

        confirmationDialog.sendCancel();
    }
    }