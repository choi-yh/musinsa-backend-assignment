// 구현 1
async function fetchLowestPriceBrandByCategory() {
    try {
        const response = await fetch('/api/v1/products/categories/lowest-prices');
        const data = await response.json();
        renderTableForLowestPrice(data, 'result-1')
    } catch (error) {
        document.getElementById('result-1').textContent = 'Error: ' + error.message;
    }
}

function renderTableForLowestPrice(data, elementId) {
    // 해당 엘리먼트를 초기화
    const resultElement = document.getElementById(elementId);
    resultElement.innerHTML = '';

    // 테이블 생성
    const table = document.createElement('table');
    table.style.borderCollapse = 'collapse';
    table.style.marginTop = '10px';

    // 테이블 헤더 생성
    const thead = document.createElement('thead');
    const headerRow = document.createElement('tr');

    const headers = ['카테고리', '브랜드', '가격'];
    headers.forEach(text => {
        const th = document.createElement('th');
        th.textContent = text;
        th.style.border = '1px solid black';
        th.style.padding = '8px';
        headerRow.appendChild(th);
    });

    thead.appendChild(headerRow);
    table.appendChild(thead);

    // 테이블 바디 생성
    const tbody = document.createElement('tbody');
    let totalPrice = 0;

    // 데이터를 테이블에 추가
    data.products.forEach(item => {
        const row = document.createElement('tr');

        const categoryCell = document.createElement('td');
        categoryCell.textContent = item.category;
        categoryCell.style.border = '1px solid black';
        categoryCell.style.padding = '8px';
        row.appendChild(categoryCell);

        const brandCell = document.createElement('td');
        brandCell.textContent = item.brand_name;
        brandCell.style.border = '1px solid black';
        brandCell.style.padding = '8px';
        row.appendChild(brandCell);

        const priceCell = document.createElement('td');
        priceCell.textContent = item.price.toLocaleString();
        priceCell.style.border = '1px solid black';
        priceCell.style.padding = '8px';
        row.appendChild(priceCell);

        // 총액 계산
        totalPrice += item.price;

        tbody.appendChild(row);
    });

    table.appendChild(tbody);

    // 총액 행 추가
    const tfoot = document.createElement('tfoot');
    const totalRow = document.createElement('tr');

    const totalLabelCell = document.createElement('td');
    totalLabelCell.textContent = '총액';
    totalLabelCell.colSpan = 2; // 두 칸을 합침
    totalLabelCell.style.border = '1px solid black';
    totalLabelCell.style.padding = '8px';
    totalRow.appendChild(totalLabelCell);

    const totalPriceCell = document.createElement('td');
    totalPriceCell.textContent = data.total_price.toLocaleString();
    totalPriceCell.style.border = '1px solid black';
    totalPriceCell.style.padding = '8px';
    totalRow.appendChild(totalPriceCell);

    tfoot.appendChild(totalRow);
    table.appendChild(tfoot);

    // 결과 엘리먼트에 테이블 추가
    resultElement.appendChild(table);
}

// 구현 2
async function fetchLowestPriceByBrand() {
    try {
        const response = await fetch('/api/v1/brands/lowest-price');
        const data = await response.json();
        document.getElementById('result-2').textContent = JSON.stringify(data, null, 2);
    } catch (error) {
        document.getElementById('result-2').textContent = 'Error: ' + error.message;
    }
}

// 구현 3
async function fetchLowestHighestBrandByCategory() {
    const category = document.getElementById('category-input').value;
    if (!category) {
        alert('카테고리를 입력하세요.');
        return;
    }

    try {
        const response = await fetch(`/api/v1/products/categories/${category}/lowest-highest`);
        const data = await response.json();
        document.getElementById('result-3').textContent = JSON.stringify(data, null, 2);
    } catch (error) {
        document.getElementById('result-3').textContent = 'Error: ' + error.message;
    }
}

