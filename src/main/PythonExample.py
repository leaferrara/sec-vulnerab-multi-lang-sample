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

# Code Injection
def GET(self):
    get_input = web.input()
    param1 = get_input['param1'] if 'param1' in get_input else None
    if (param1):
        x = eval(param1)
    return "I'm vulnerable"

# CWE-759
def storePassword(userName,Password):
    hasher = hashlib.new('md5')
    hasher.update(Password)
    hashedPassword = hasher.digest()

    # UpdateUserLogin returns True on success, False otherwise
    return updateUserLogin(userName,hashedPassword)