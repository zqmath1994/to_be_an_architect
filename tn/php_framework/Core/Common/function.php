<?php

function p($val)
{
    if(is_bool($val)){
        var_dump($val);
    }else if(is_null($val)){
        var_dump(NULL);
    }else{
        echo "<pre style='position:relative;z-index:1000;padding:10px;border-radius:5px;background:#F5F5F5;border:1px solid #aaa;font-size:14px;
        line-height:18px;opacity:0.9;'>".$val."</pre>";
    }
}