class PythonExample():
    "This is an example class"
    a = 10
    def func(self):
        print('Hello Example')

# Output: 10
print(PythonExample.a)

# Output: <function MyClass.func at 0x0000000003079BF8>
print(PythonExample.func)

# Output: 'This is my second class'
print(PythonExample.__doc__)
