const addBtns = document.querySelectorAll(".add-btn:not(.solid)");
const saveItemBtns = document.querySelectorAll(".solid");
const addItemContainers = document.querySelectorAll(".add-container");
const addItems = document.querySelectorAll(".add-item");

const listColumns = document.querySelectorAll(".drag-item-list");
const backlogListEl = document.getElementById("to-do-list");
const progressListEl = document.getElementById("doing-list");
const completeListEl = document.getElementById("done-list");


let updatedOnLoad = false;

let draggedItem;
let dragging = false;
let currentColumn;

function addOrUpdTask(taskTitle, taskStatus, projectId, taskId, mode){
    const url = '/tasks'; // Replace with your API URL
    let params = {
        taskTitle: taskTitle,
        taskStatus: taskStatus,
        projectId: projectId,
    };

    if(mode === 'update'){
        params.taskId = taskId;
    }

    const searchParams = new URLSearchParams(params).toString();

    const xhr = new XMLHttpRequest();
    xhr.open('POST', url, false); // Synchronous request

    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); // Set the necessary Content-Type
    xhr.send(searchParams);
    if(xhr.status===200){
        return xhr.responseText;
    } else return '-1'
}

function deleteTask(taskId){
    const url = '/tasks';
    let params = {
        taskId: taskId,
        _method: 'DELETE'
    };


    const searchParams = new URLSearchParams(params).toString();

    const xhr = new XMLHttpRequest();
    xhr.open('POST', url, false); // Synchronous request

    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(searchParams);
}

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

function filterArray(array) {
    return array.filter((item) => item !== null);
}


function createItemEl(columnEl, column, item, index) {
    const listEl = document.createElement("li");

    listEl.addEventListener('blur', function(event) {
        const changedValue = event.target.textContent;
        console.log('Input value changed:', changedValue);
    });

    listEl.textContent = item;
    listEl.id = index;
    listEl.classList.add("drag-item");
    listEl.draggable = true;
    listEl.setAttribute("taskid", item.id)
    listEl.setAttribute("taskstatus", item.status)
    listEl.setAttribute("onfocusout", `updateItem(${index}, ${column})`);
    listEl.setAttribute("ondragstart", "drag(event)");
    listEl.contentEditable = true;
    columnEl.appendChild(listEl);


}
function updateDOM() {
    if (!updatedOnLoad) {
        getSavedColumns();
    }
    backlogListEl.textContent = "";
    backlogListArray.forEach((backlogItem, index) => {
        createItemEl(backlogListEl, 0, backlogItem, index);
    });
    backlogListArray = filterArray(backlogListArray);
    progressListEl.textContent = "";
    progressListArray.forEach((progressItem, index) => {
        createItemEl(progressListEl, 1, progressItem, index);
    });
    progressListArray = filterArray(progressListArray);
    completeListEl.textContent = "";
    completeListArray.forEach((completeItem, index) => {
        createItemEl(completeListEl, 2, completeItem, index);
    });
    completeListArray = filterArray(completeListArray);
    updatedOnLoad = true;

    updateSavedColumns();
}

function updateItem(id, column) {
    const selectedArray = listArrays[column];
    const selectedColumn = listColumns[column].children;
    if (!dragging) {
        if (!selectedColumn[id].textContent) {
            deleteTask(selectedArray[id].id);
            delete selectedArray[id];
        } else {
            addOrUpdTask(selectedColumn[id].textContent, column, projectId, selectedArray[id].id, 'update');
            selectedArray[id].name = selectedColumn[id].textContent;
        }
        updateDOM();
    }
}

function addToColumn(column) {
    const itemText = addItems[column].textContent;
    const selectedArray = listArrays[column];
    let id = addOrUpdTask(itemText, column, projectId, null, 'create');
    if (id==='-1') return;
    listArrays[column].push(new TodoTask(parseInt(id), itemText));
    addItems[column].textContent = "";
    updateDOM(column);
}

function showInputBox(column) {
    addBtns[column].style.visibility = "hidden";
    saveItemBtns[column].style.display = "flex";
    addItemContainers[column].style.display = "flex";
}
function hideInputBox(column) {
    addBtns[column].style.visibility = "visible";
    saveItemBtns[column].style.display = "none";
    addItemContainers[column].style.display = "none";
    addToColumn(column);
}

function rebuildArrays() {
    backlogListArray = [];
    for (let i = 0; i < backlogListEl.children.length; i++) {
        backlogListArray.push(new TodoTask(
            backlogListEl.children[i].getAttribute("taskid"),
            backlogListEl.children[i].textContent,
            backlogListEl.children[i].getAttribute("taskstatus")
            ));
    }
    progressListArray = [];
    for (let i = 0; i < progressListEl.children.length; i++) {
        progressListArray.push(new TodoTask(
            progressListEl.children[i].getAttribute("taskid"),
            progressListEl.children[i].textContent,
            progressListEl.children[i].getAttribute("taskstatus")
        ));
    }
    completeListArray = [];
    for (let i = 0; i < completeListEl.children.length; i++) {
        completeListArray.push(new TodoTask(
            completeListEl.children[i].getAttribute("taskid"),
            completeListEl.children[i].textContent,
            completeListEl.children[i].getAttribute("taskstatus")
        ));
    }
    updateDOM();
}

function dragEnter(column) {
    listColumns[column].classList.add("over");
    currentColumn = column;
}

function drag(e) {
    draggedItem = e.target;
    dragging = true;
}

function allowDrop(e) {
    e.preventDefault();
}

function drop(e) {
    e.preventDefault();
    console.log(draggedItem)
    let id = addOrUpdTask(draggedItem.textContent, currentColumn, projectId, draggedItem.getAttribute("taskid"), 'update');
    if (id==='-1') return;
    const parent = listColumns[currentColumn];
    listColumns.forEach((column) => {
        column.classList.remove("over");
    });
    parent.appendChild(draggedItem);
    dragging = false;
    rebuildArrays();
}
updateDOM();