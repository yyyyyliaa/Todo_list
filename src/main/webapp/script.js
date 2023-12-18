const addBtns = document.querySelectorAll(".add-btn:not(.solid)");
const saveItemBtns = document.querySelectorAll(".solid");
const addItemContainers = document.querySelectorAll(".add-container");
const addItems = document.querySelectorAll(".add-item");

// Item Lists
const listColumns = document.querySelectorAll(".drag-item-list");
const backlogListEl = document.getElementById("to-do-list");
const progressListEl = document.getElementById("doing-list");
const completeListEl = document.getElementById("done-list");


class TodoTask {
    constructor(id, name) {
        this.id = id;
        this.name = name;
    }

    toString() {
        return this.name;
    }
}
// Items
let updatedOnLoad = false;

// Initialize Arrays
let backlogListArray = [];
let progressListArray = [];
let completeListArray = [];
let listArrays = [];

// Drag Functionality
let draggedItem;
let dragging = false;
let currentColumn;

// Get Arrays from localStorage if available, set default values if not
function getSavedColumns() {
    backlogListArray = [new TodoTask(1, 'John'), new TodoTask(3, 'ggапg')]
    progressListArray = [];
    completeListArray = [new TodoTask(2, 'ggg')];

    // backlogListArray = ["Доделать курсач"]
    // progressListArray = ["Work on Droppi project", "Listen to Spotify"];
    // completeListArray = ["Submit a PR", "Review my projects code"];

    // if (localStorage.getItem("backlogItems")) {
    //     backlogListArray = JSON.parse(localStorage.backlogItems);
    //     progressListArray = JSON.parse(localStorage.progressItems);
    //     completeListArray = JSON.parse(localStorage.completeItems);
    //     onHoldListArray = JSON.parse(localStorage.onHoldItems);
    // } else {
    //     const intro = prompt(
    //         "Type 'y' (Yes) if you want to display an Editable Sample? \n(Not typing 'y' will display a plane NEW board.)"
    //     );
    //     if (intro === "y" || intro === "Y") {
    //         backlogListArray = [
    //             "Write the documentation",
    //             "Post a technical article",
    //         ];
    //         progressListArray = ["Work on Droppi project", "Listen to Spotify"];
    //         completeListArray = ["Submit a PR", "Review my projects code"];
    //         onHoldListArray = ["Get a girlfriend"];
    //     } else {
    //         backlogListArray = [];
    //         progressListArray = [];
    //         completeListArray = [];
    //         onHoldListArray = [];
    //     }
    // }
}

// Set localStorage Arrays
function updateSavedColumns() {
    listArrays = [
        backlogListArray,
        progressListArray,
        completeListArray,
    ];
    const arrayNames = ["backlog", "progress", "complete"];
    arrayNames.forEach((arrayName, index) => {
        localStorage.setItem(
            `${arrayName}Items`,
            JSON.stringify(listArrays[index])
        );
    });

}

// Filter Array to remove empty values
function filterArray(array) {
    return array.filter((item) => item !== null);
}


function createItemEl(columnEl, column, item, index) {
    const listEl = document.createElement("li");
    listEl.textContent = item;
    listEl.id = index;
    listEl.classList.add("drag-item");
    listEl.draggable = true;
    listEl.setAttribute("onfocusout", `updateItem(${index}, ${column})`);
    listEl.setAttribute("ondragstart", "drag(event)");
    listEl.contentEditable = true;
    columnEl.appendChild(listEl);
}

// Update Columns in DOM - Reset HTML, Filter Array, Update localStorage
function updateDOM() {
    // Check localStorage once
    // getSavedColumns();
    if (!updatedOnLoad) {
        getSavedColumns();
    }
    // Backlog Column
    backlogListEl.textContent = "";
    backlogListArray.forEach((backlogItem, index) => {
        createItemEl(backlogListEl, 0, backlogItem, index);
    });
    backlogListArray = filterArray(backlogListArray);
    // Progress Column
    progressListEl.textContent = "";
    progressListArray.forEach((progressItem, index) => {
        createItemEl(progressListEl, 1, progressItem, index);
    });
    progressListArray = filterArray(progressListArray);
    // Complete Column
    completeListEl.textContent = "";
    completeListArray.forEach((completeItem, index) => {
        createItemEl(completeListEl, 2, completeItem, index);
    });
    completeListArray = filterArray(completeListArray);
    // Run getSavedColumns only once, Update Local Storage
    updatedOnLoad = true;
    alert("yyyyyyyy")
    updateSavedColumns();
}

// Update Item - Delete if necessary, or update Array value
function updateItem(id, column) {
    const selectedArray = listArrays[column];
    const selectedColumn = listColumns[column].children;
    if (!dragging) {
        if (!selectedColumn[id].textContent) {
            delete selectedArray[id];
        } else {
            selectedArray[id] = selectedColumn[id].textContent;
        }
        updateDOM();
    }
}

// Add to Column List, Reset Textbox
function addToColumn(column) {
    const itemText = addItems[column].textContent;
    const selectedArray = listArrays[column];

    listArrays[column].push(new TodoTask(5, itemText));
    alert("ffff")
    addItems[column].textContent = "";

    updateDOM(column);
}

// Show Add Item Input Box
function showInputBox(column) {
    addBtns[column].style.visibility = "hidden";
    saveItemBtns[column].style.display = "flex";
    addItemContainers[column].style.display = "flex";
}

// Hide Item Input Box
function hideInputBox(column) {
    addBtns[column].style.visibility = "visible";
    saveItemBtns[column].style.display = "none";
    addItemContainers[column].style.display = "none";
    addToColumn(column);
}

// Allows arrays to reflect Drag and Drop items
function rebuildArrays() {
    backlogListArray = [];
    for (let i = 0; i < backlogListEl.children.length; i++) {
        backlogListArray.push(backlogListEl.children[i].textContent);
    }
    progressListArray = [];
    for (let i = 0; i < progressListEl.children.length; i++) {
        progressListArray.push(progressListEl.children[i].textContent);
    }
    completeListArray = [];
    for (let i = 0; i < completeListEl.children.length; i++) {
        completeListArray.push(completeListEl.children[i].textContent);
    }
    updateDOM();
}

// When Item Enters Column Area
function dragEnter(column) {
    listColumns[column].classList.add("over");
    currentColumn = column;
}

// When Item Starts Dragging
function drag(e) {
    draggedItem = e.target;
    dragging = true;
}

// Column Allows for Item to Drop
function allowDrop(e) {
    e.preventDefault();
}

// Dropping Item in Column
function drop(e) {
    e.preventDefault();
    const parent = listColumns[currentColumn];
    // Remove Background Color/Padding
    listColumns.forEach((column) => {
        column.classList.remove("over");
    });
    // Add item to Column
    parent.appendChild(draggedItem);
    // Dragging complete
    dragging = false;
    rebuildArrays();
}

// On Load
updateDOM();