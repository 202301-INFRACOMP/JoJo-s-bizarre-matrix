package edu.jojos.bizarre.matrix.aging;

import edu.jojos.bizarre.matrix.paging.Page;
import edu.jojos.bizarre.matrix.paging.PageDirectory;

import java.util.List;

public class Ager {
    private PageDirectory pageDirectory;



    public void agePages() {
        List<Page> pages = pageDirectory.getPages();
        for (Page page : pages) {
            page.setLastAccessed(page.getLastAccessed() + 1);
        }
    }

    private Page selectPageToReplace(){
        //select the page that was accessed the longest time ago
        List<Page> pages = pageDirectory.getPages();
        Page oldestPage = pages.get(0);
        for (Page page : pages) {
            if (page.getLastAccessed() < oldestPage.getLastAccessed()) {
                oldestPage = page;
            }
        }
        return oldestPage;
    }

}
