<#list posts as post>
    <div class="post">
        
        <p>${post.content}</p>
        
        <h3>Categories:</h3>
        <ul class="categories">
            <#list post.categories as category>
                <li>${category}</li>
            </#list>
        </ul>
        <i>Published on the ${post.publishing_date}</i>
    </div>
</#list>