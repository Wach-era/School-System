// Sample Data (Replace with data from your JSP backend)
let students = [
    { id: 1, name: "John Doe", email: "john@example.com", course: "Computer Science", lastAccessed: new Date() },
    { id: 2, name: "Jane Smith", email: "jane@example.com", course: "Mathematics", lastAccessed: new Date() },
    // Add more students as needed
];

let currentPage = 1;
const rowsPerPage = 10;

// DOM Elements
const tableBody = document.querySelector("#studentTable tbody");
const searchInput = document.getElementById("searchInput");
const sortBy = document.getElementById("sortBy");
const prevPageBtn = document.getElementById("prevPage");
const nextPageBtn = document.getElementById("nextPage");
const pageInfo = document.getElementById("pageInfo");

// Render Table
function renderTable(page = 1) {
    tableBody.innerHTML = "";
    const start = (page - 1) * rowsPerPage;
    const end = start + rowsPerPage;

    // Sort students based on selected option
    const sortValue = sortBy.value;
    let sortedStudents = [...students];

    if (sortValue === "recent") {
        sortedStudents.sort((a, b) => b.lastAccessed - a.lastAccessed);
    } else if (sortValue === "name_asc") {
        sortedStudents.sort((a, b) => a.name.localeCompare(b.name));
    } else if (sortValue === "name_desc") {
        sortedStudents.sort((a, b) => b.name.localeCompare(a.name));
    } else if (sortValue === "date_asc") {
        sortedStudents.sort((a, b) => a.id - b.id);
    } else if (sortValue === "date_desc") {
        sortedStudents.sort((a, b) => b.id - a.id);
    }

    const paginatedStudents = sortedStudents.slice(start, end);

    paginatedStudents.forEach(student => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${student.name}</td>
            <td>${student.email}</td>
            <td>${student.course}</td>
            <td>
                <div class="actions-dropdown">
                    <button class="btn">Actions</button>
                    <div class="dropdown-content">
                        <a href="${pageContext.request.contextPath}/students/edit?id=${student.studentId}" class="edit-btn">Edit</a>
                        <a href="${pageContext.request.contextPath}/students/delete?id=${student.studentId}" class="delete-btn" onclick="return confirm('Are you sure you want to delete this student?');">Delete</a>
                        <a href="${pageContext.request.contextPath}/reportservlet/generate?studentId=${student.studentId}" class="report-btn">Generate Report</a>
                    </div>
                </div>
            </td>
        `;
        tableBody.appendChild(row);
    });

    pageInfo.textContent = `Page ${page} of ${Math.ceil(students.length / rowsPerPage)}`;
}

// Event Listeners
sortBy.addEventListener("change", () => {
    renderTable(currentPage);
});

searchInput.addEventListener("input", (e) => {
    const searchTerm = e.target.value.toLowerCase();
    const filteredStudents = students.filter(student =>
        student.name.toLowerCase().includes(searchTerm) ||
        student.email.toLowerCase().includes(searchTerm) ||
        student.course.toLowerCase().includes(searchTerm)
    );
    students = filteredStudents;
    renderTable();
});

prevPageBtn.addEventListener("click", () => {
    if (currentPage > 1) {
        currentPage--;
        renderTable(currentPage);
    }
});

nextPageBtn.addEventListener("click", () => {
    if (currentPage < Math.ceil(students.length / rowsPerPage)) {
        currentPage++;
        renderTable(currentPage);
    }
});

// Initial Render
renderTable(currentPage);