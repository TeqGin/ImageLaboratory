import ctypes
import time
import os

# start time
t0 = time.time()

os.environ["path"] += ";F:/Task/windowsDLL/testDLL/release"
lib = ctypes.cdll.LoadLibrary("F:/Task/windowsDLL/testDLL/release/FaceEngineSDK.dll")


class SeetaFaceIdInfo(ctypes.Structure):
    _fields_ = [
        ("faceNum", ctypes.c_int),
        ("faceDataSize", ctypes.c_int),
        ("faceData", ctypes.POINTER(ctypes.c_char))
    ]


lib.faceTracker.argtypes = [ctypes.c_char_p, ctypes.c_char_p]
lib.faceTracker.restype = SeetaFaceIdInfo

lib.safeReleasePointer.argtypes = [ctypes.POINTER(ctypes.c_char)]
lib.safeReleasePointer.restype = ctypes.c_int

model_path = 'F:/Task/windowsDLL/testDLL/models/'
model_path_buff = ctypes.create_string_buffer(model_path.encode('utf-8'))

video_path = 'F:/Task/windowsDLL/images/1.mp4'
video_path_buff = ctypes.create_string_buffer(video_path.encode('utf-8'))

res = lib.faceTracker(model_path_buff, video_path_buff)

print("faceNum: ", res.faceNum)
print("faceDataSize: ", res.faceDataSize)

faceData = [res.faceData[i].decode('utf-8') for i in range(res.faceDataSize)]
print(''.join(faceData).split(","))

lib.releasePointer.argtypes = [ctypes.POINTER(ctypes.c_float)]
lib.releasePointer.restype = ctypes.c_int

code = lib.safeReleasePointer(res.faceData)
print()
print("status code:", code)

t1 = time.time()
print("cost time: {:.2f} ms".format(1000 * (t1 - t0)))
