现在开始记笔记！！！
#git
    
###1. 创建版本库
* 创建一个目录：learngit，这个目录就是工作区。
* 进入目录，使用 git init 命令初始化，learngit目录下会出现一个.git目录，这个目录就是版本库（也叫仓库）(work dict)。
* 在learngit下创建一个文件 test.txt，这个文件在工作区；使用 git add test.txt 命令，这时文件就被放到了暂存区(stage)；（可以使用git status查看状态）。
* 把文件提交到仓库： git commit -m "提交说明" ，文件从暂存区取出并被提交到当前分支。

#### git add :
* git add -A   // 添加所有改动
* git add *     // 添加新建文件和修改，但是不包括删除
* git add .    // 添加新建文件和修改，在git 1.X版本不包括删除，2.X版本包括删除，和 git add -A 一样
* git add -u   // 添加修改和删除，但是不包括新建文件
#### git checkout -- :
     在 add 前撤销修改，也就是撤销工作区的修改 
     eg:
        git checkout -- 'test.txt'
#### git reset : 在 commit 前撤销 add，即清空暂存区
     git reset <file>:撤销文件file  eg:假设使用 git add . ，但现在不想提交其中某个文件，就可以使用 git reset +文件名 
     git reset : all


###2. 版本回退
可以使用 git log 命令查看历史，（下面会区别 git reflog 和 git log ）； HEAD 表示当前版本，HEAD^ 表示上一个版本， HEAD^^ 表示上上个，也可以用 HEAD~数字 。
回退到上一版本： git reset --hard HEAD^ ，也可以直接用 git reset --hard commit-id 。
* git log：查看提交历史，包含很多信息，但是回退到某一版本之后，这一版本之后的记录将不会显示。
* git reflog：提交的全部历史记录。

git diff:
* git diff 查看当前工作区与暂存区的差别
* git diff --cached 查看暂存区和分支的差别

rm 与 git rm:
rm 删除文件后可以通过 git checkout -- 命令恢复，git rm 将会从版本库删除，不能通过 git checkout -- 恢复


###3. 远程库
以github为例。
之前我们已经在本地创建好了版本库，现在github上创建一个版本库：点击new repository，然后就创建了一个git版本库。接下来根据github的提示操作：
* git remote add origin https://github.com/*****/learngit.git   #添加远程版本库
* git push -u origin master                                     #首次将本地master分支提交到远程master分支
  本地提交后，可以通过 git push origin master 命令把本地master分支的修改提交到github。
  推送本地更新到远程服务器: git push <远程主机名> <本地分支>:<远程分支> 
  eg: 
  git push origin master:master #远程主机名origin,本地分支master,远程分支master
  git push origin master        #省略远程分支名，表示将本地分支推送到与之存在追踪关系的远程分支（本地和远程分支都可以省略：git push），如果该远程分支不存在，则会被新建；
                                #如果当前分支只有一个远程分支，那么主机名都可以省略，形如 git push，可以使用git branch -r ，查看远程的分支名
  git push origin:master        #省略本地分支名，则表示删除指定的远程分支，因为这等同于推送一个空的本地分支到远程分支，等同于 git push origin --delete master
   
也可以直接使用 git clone 命令从github上克隆到本地，git从远程的分支获取最新版本到本地有下面两个命令:
* git fetch : 不会自动merge 
* git pull : 从远程库获取最新版本并merge到本地 eg : git pull origin master 
    若使用 git fetch:
    1. git fetch origin master           #从远程的origin的master主分支下载最新的版本到origin/master分支上
    2. git log -p master..origin/master  #比较本地master分支和origin/master分支的差别
    3. git merge origin master           #合并分支

###4. 分支管理
通常我们需要在分支上工作，工作完成后再合并到原来的分支。
* 创建一个叫dev的分支：git branch dev
* 切换到dev分支：git checkout dev (可以使用 git checkout -b 命令创建并切换到dev分支)
* 查看分支：git branch
接下来在dev分支工作，提交修改后，切换回master分支后就会发现所有内容都没发生变化，因为刚刚的提交是在dev分支上。
* 把dev分支合并到master上：git merge dev , 也就是把master指向dev的当前提交   # git merge 命令合并指定分支到当前分支
* 删除dev分支：git branch -d dev
* git log --graph (可以加 --pretty=oneline --abbrev-commit) 查看分支合并图

如果当前分支和待合并分支同时修改了某个文件，那么合并时就会发生冲突。
解决冲突：先修改冲突的文件，修改后提交，一定要提交！！！最后合并分支。

当你正在dev分支开发时，临时需要在master分支改一个bug，由于当前功能未开发完，不能提交，而如果直接切换到master，dev分支中开发的代码就会出现在master中，
所以最好将dev分支工作区内容隐藏，再切换到master中创建分支修改bug。
* git stash : 备份当前的工作区的内容，从最近的一次提交中读取相关内容，让工作区保证和上次提交的内容一致。同时，将当前的工作区内容保存到Git栈中。
              当需要临时切换到别的分支解决问题时，可以把当前工作区隐藏。
* git stash pop : 相当于先 git stash apply 恢复，再 git stash drop 删除 stash 内容。
* git stash list : 查看 stash , 然后可以用 git stash apply 恢复指定stash。
当你正在dev分支开发时，临时需要添加一个新功能，最好新建一个feature分支，在这个分支上开发，完成后再合并。

#####远程：
当你从远程仓库克隆时，实际上Git自动把本地的master分支和远程的master分支对应起来了，并且，远程仓库的默认名称是origin。要查看远程库的信息，用git remote。
更详细地，可以用 git remote -v , 将显示可抓取地址和可推送地址。
要想在dev分支上开发，首先需要创建远程origin的分支dev到本地：git checkout -b dev origin/dev , 这样，就可以在dev分支上向远程提交了。
如果另一个人已经向远程提交了同个文件，那么你再提交时就会发生冲突，根据提示，先用 git pull 把远程的更新抓到本地合并，在本地解决冲突，然后提交，最后push。

注：如果 git pull 提示“no tracking information”，说明没有建立远程和本地的连接，用 git branch --set-upstream branch-name origin/branch-name。


