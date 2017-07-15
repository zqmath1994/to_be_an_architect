#数据类型
###整数

	带符号： int int8 int16 int32 int64
	不带符号： uint unit8 uint16 uint32 uint64
	不同类型必须强制转换：
		var value2 int32
		value1 := 64 //value1将会自动推导为int类型
		value2 = value1 //编译错误
		value2 = int32（value1） //正确

	两个不同类型的数值不能直接比较

还有2个特殊的整型别名 ，和字符串很相关，分别是：byte(uint8)，rune(int32)，到字符串相关时在做解释
 
整数比较用 > / < / == / != / <= / >=，结果是布尔类型， true or false
 
按位运算 &（且）， |（或）， ^（异或），&^（与非），<<（左移），>>（右移）
异或自身，相当于取反码， 例如 ^1 = -2（ps：(^-1) + 1 = 求-1的补码）

###浮点数

	float32 float64
	浮点数不要比较，有一种替换方案
	import "math"
	//p为用户自动义的比较精度，比如0.00001
	func IsEqual(f1,f2,p float64) bool {
		return math.Fdim(f1,f2) < p 
	}
###布尔
 
	true  false
三种逻辑运算，&&，||，！
布尔类型不支持其他类型的转换

###字符串
	string
	var str string
	str = "hello"
	ch ：= str[0]
	len(str)
字符串操作：
	x+y 字符串连接 "hello"+"123" 
	len(s) 字符串长度 len("hello") //结果为5
	s[i] 取字符   "hello"[1] //结果为'e'
更多strings包

字符串遍历：
	str := "hello,十二届"
	n := len(str)
	for i := 0; i<n;i++ {
		ch := str[i]	//以字节数组遍历，byte类型
		fmt.Println(i,ch)
	}	
	//以unicode字符遍历
	str := "hello,世界"
	for i,ch := range str {
		fmt.Println(i,ch) //ch的类型为rune
	}

组成字符串的最小单位是字符，存储的最小单位是字节，字符串本身不支持修改。

###字符类型
	byte实际上是unit8的别名，代表UTF-8的单个字节的值
	rune代表Unicode字符


###错误类型
	error

###指针
	pointer

###数组
常规的数组声明方法：
	[32]byte
	[2*N] struct {x ,y int32} //复杂数组类型
	[1000]*float64 //指针数组
	[3][5]int	//二维数组
	[2][2][2]float64 
一旦数组被声明了，那么它的数据类型跟长度都不能再被改变
	获取数组arr元素的个数
	arrlength := len(arr)
元素访问：
	for i := 0; i <len(array);i++ {
		fmt.Println("Element",i,"of array is",array[i])
	}
	
	for i,v :=- range array {
		fmt.Println("Array element[",i,"]=",v)
	}
数组切片：
	package main
	import (
		"fmt"
	)
	func main() {
		//先定义一个数组
		var myArray [5]int = [5]int{1,2,3,4,5}
		//基于数组创建一个数组切片
		var mySlice []int = myArray[:3]
		fmt.Println("Elements of myArray: ")

		for _,v := range myArray {
			fmt.Print(v," ")
		}

		fmt.Println("\nElements of mySlice: ")
		for _,v := range mySlice {
			fmt.Print(v," ")
		}
		fmt.Println()
	}
	基于myArray的所有元素创建数组切片：
	mySlice = myArray[:]
	基于myArray的前3个元素创建数组切片：
	myslice := myArray[:3]
	基于myArray的第3个元素创建数组切片：
	myslice := myArray[3:]


数组在GO语言中是一个值类型。所有值类型变量在函数中作为参数传递时都将产生一次复制动作。数组作为函数的参数类型，修改数组将不会成功
	

	
var array [5]int   



// 声明一个长度为5的整数数组
// 初始化每个元素

array := [5]int{7, 77, 777, 7777, 77777}

如果你把长度写成 ...，Go 编译器将会根据你的元素来推导出长度：

// 通过初始化值的个数来推导出数组容量

array := [...]int{7, 77, 777, 7777, 77777}

// 声明一个长度为5的整数数组

// 为索引为1和2的位置指定元素初始化

// 剩余元素为0值

array := [5]int{1: 77, 2: 777}

array := [5]*int{0: new(int), 1: new(int)}

// 为索引为0和1的元素赋值

*array[0] = 7

*array[1] = 77

###常量
	const (
	        e  = 2.71
	        pi = 3.14
	)

	 type Weekday int
	 const (
	         Sunday Weekday = iota
 	         Monday
     	     Tuesday
    	     Wednesday
    	     Thursday
	         Friday
	         Saturday
	       )
常量的值不可修改

数组指针：
	//给数组清零
	func zero(ptr *[32]byte) {	
	for i := range ptr {
		ptr[i] = 0
		}
	}
	
	func zero(ptr *[32]byte) {
		*ptr = [32]byte{}
	}

###Slice
切片不能比较，与数组不同，不能使用==操作符判断两个slice是否含有全部相等的元素。
定义切片：
	s := []int{0,1,2,3,4,5}
使用slice：
	s[:2]
	s[:]
	s
切片操作不能超过cap的上限，超过将会导致一个panic异常，但是超过len意味着扩展



	
#switch语句

1.最简单的switch语句

	switch content {
	default:
		fmt.Println("unkown")
	case "python":
		fmt.Println("a interpreter language!")
	case "Go":
		fmt.Println("a compiled language")
	}

2.使用函数返回值作为条件

	switch content := getContent(); content {
	default: 
		fmt.Println("unkown")
	case "python":
		fmt.Println("a interpreter language")
	case "Go":
		fmt.Println("a compile language")
	}


3.关键字fallthrough，break,多个条件“，”

	switch content：=getContent；content {
	default：
		fmt.Println("unkonw")
	case "Ruby":
		fallthrough
	case "python":
		fmt.Println("a interpreted Language")
	case "break":
		break
	case "C","java","GO":
		fmt.Println("A compile language")
	}

4.类型匹配

	switch v.(type) {
	case string:
		fmt.Printf("The string is '%s' .\n", v.(string))
	case int, uint, int8, int16, int32, uint32,uint64,int64:
		fmt.Printf("The integer is %d. \n",v)
	default:
		fmt.Printf("Unsupport value. (type=%T)\n",v)
	}	


	switch i := v.(type) {
	case string:
		fmt.Printf("The string is '%s' .\n",i)
	case int, uint:
		fmt.Printf("The integer is %d .\n",i)
	default:
		fmt.Printf("Unsupport value. (type=%T) \n",i)
	}

5.更多惯用法

	switch {
	case number < 100:
		number++
	case number < 200:
		number--
	default:
		number -= 2
	}

#for语句
1.组成和编写方法
	for number < 200 {
	number += 2
	}
	//死循环
	for {
		number++
	}

2.for字句
	
	for i := 0;i < 100;i++ {
		number++
	}
	
	var j uint = 1
	for ;j%5 !=0; j *= 3 {	//省略初始化字句
		number++
	}

	for k := 1; k%5 != 0; { //省略后置语句
		k *= 3
		number++
	}

3.range字句
	ints := []int{1,2,3,4,5,}
	for i,d := range ints {
	fmt.Printf("%d: %d\n",i,d)
	}	

	var i,d int
	for i,d = range ints {
	 fmt.Printf("%d:%d\n",i,d)	
	}

	ints := []int{1,2,3,4,5}
	length := len(ints)
	indexesMirror := make([]int,length)
	elementsMirror := make([]int,length)
	var i int
	for indexesMirror[length-i-1],elementsMirror[length-i-1] = range ints {
	 i++
	}

	ints := []int{1,2,3,4,5}
	for i := range ints{
	d := ints[i]
	fmt.Printf("%d: %d\n",i,d)
	}

4.更多惯用法
	m := map[unit]string{1: "A",6:"C",7:"B"}
	var maxKey uint
	for k := range m {
		if k > maxKey {
			maxKey = k
		}
	}
