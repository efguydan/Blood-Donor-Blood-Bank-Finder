package com.efedaniel.bloodfinder.utils

class Data {
    companion object {
        val userTypes = listOf(
            "Select User Type",
            "Blood Donor",
            "Hospital",
            "Private Blood Bank"
        )
        val titleTypes = listOf(
            "Select Title",
            "Chief",
            "Dr.",
            "Miss",
            "Mr.",
            "Mrs."
        )
        val genderTypes = listOf(
            "Select Gender",
            "Male",
            "Female",
            "Prefer Not To Say"
        )
        val religionTypes = listOf(
            "Select Religion",
            "Christianity",
            "Muslim",
            "Traditional",
            "Prefer Not To Say"
        )
        val maritalStatusTypes = listOf(
            "Select Marital Status",
            "Divorced",
            "Married",
            "Single",
            "Widowed",
            "Prefer Not To Say"
        )
        val bloodDonorActionList = listOf(
            "Upload Blood Availability",
            "View Profile",
            "View Donation History",
            "Logout"
        )
        val hospitalActionList = listOf(
            "Request For Blood",
            "View Profile",
            "View Request History",
            "Logout"
        )
        val bloodBankActionList = listOf(
            "Upload Blood Availability",
            "Request For Blood",
            "View Profile",
            "View Request History",
            "View Donation History",
            "Logout"
        )
        val bloodTypes = listOf(
            "Select Blood Type",
            "A+",
            "A-",
            "B+",
            "B-",
            "AB+",
            "AB-",
            "O+",
            "O-"
        )
        val kindOfBloodDonor = listOf(
            "Select Kind of Blood Donor",
            "Blood Donor",
            "Private Blood Bank",
            "Any"
        )
        val billingType = listOf(
            "Select Billing Type",
            "Free",
            "Paid"
        )
        val billingTypeWithAny = listOf(
            "Select Billing Type",
            "Free",
            "Paid",
            "Any"
        )
        val bloodCompatibilityMapping = mapOf(
            "A+" to listOf("A+", "A-", "O+", "O-"),
            "A-" to listOf("A-", "O-"),
            "B+" to listOf("B+", "B-", "O+", "O-"),
            "B-" to listOf("B-", "O-"),
            "AB+" to listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"),
            "AB-" to listOf("A-", "B-", "AB-", "O-"),
            "O+" to listOf("O+", "O-"),
            "O-" to listOf("O-")
        )
    }
}
