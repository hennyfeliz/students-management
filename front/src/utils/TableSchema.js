export const TableSchemes = [
  {
    path: "school",
    title: "Schools",
    table_headers: ["School Name", "Address", "Phone Number"],
    table_body: ["schoolName", "address", "phoneNumber"],
  },
  {
    path: "student",
    title: "Students",
    table_headers: ["Student Name", "Grade Level", "School Name"],
    table_body: ["studentName", "gradeLevel", "school.schoolName"],
  },
];
