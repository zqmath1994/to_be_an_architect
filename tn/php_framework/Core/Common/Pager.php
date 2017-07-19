<?php
namespace Core\Common;

/**
 * @version: V1.0
 * @author: yy
 */
class Pager
{
    public $totalPages;
    public $first = 1;
    public $prev;
    public $curr;
    public $next;
    public $last;
    public $totalItems;
    public $pageNum = 10;
    public $itemNum = 20;
    public $url;

    public function setCurrPage($currPage)
    {
        $this->curr = $currPage;
        return $this;
    }

    public function setPageNum($pageNum=10)
    {
        $this->pageNum = $pageNum;
        return $this;
    }

    public function setItemNum($itemNum)
    {
        $this->itemNum = $itemNum;
        return $this;
    }

    public function settotalItems($totalItems)
    {
        $this->totalItems = $totalItems;
        return $this;
    }

    public function setUrl($url)
    {
        $this->url = $url;
        return $this;
    }

    public function finish()
    {
        $this->totalPages = ceil($this->totalItems/$this->itemNum);
        $this->last = $this->totalPages;
        $this->prev = ($this->curr-1) < $this->first ? $this->first : ($this->curr-1);
        $this->curr = $this->curr < $this->last ? $this->curr : $this->last;
        $this->next = ($this->curr+1) < $this->last ? ($this->curr+1) : $this->last;
        return $this;
    }
}